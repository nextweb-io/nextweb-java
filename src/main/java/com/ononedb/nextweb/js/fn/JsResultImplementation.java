package com.ononedb.nextweb.js.fn;

import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.ExceptionManager;

import java.util.LinkedList;
import java.util.List;

public class JsResultImplementation<ResultType> implements Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;

	private ResultType resultCache;

	private final ExceptionManager exceptionManager;

	private boolean requestingResult;

	private final List<ResultCallback<ResultType>> deferredCalls;

	private void requestResult(final ResultCallback<ResultType> callback) {

		if (resultCache != null) {
			callback.onSuccess(resultCache);
			return;
		}

		if (requestingResult) {
			deferredCalls.add(callback);
			return;
		}

		asyncResult.get(new ResultCallback<ResultType>() {

			@Override
			public void onSuccess(ResultType result) {
				resultCache = result;
				requestingResult = false;
				callback.onSuccess(result);

				for (ResultCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onSuccess(result);
				}
				deferredCalls.clear();
			}

			@Override
			public void onFailure(Throwable t) {
				requestingResult = false;
				callback.onFailure(t);
				for (ResultCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onFailure(t);
				}
				deferredCalls.clear();

			}

		});

	}

	@Override
	public void get(final ResultCallback<ResultType> callback) {
		requestResult(callback);
	}

	@Override
	public void catchExceptions(ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
	}

	/**
	 * Will trigger a request
	 */
	@Override
	public ResultType get() {

		requestResult(new ResultCallback<ResultType>() {

			@Override
			public void onSuccess(ResultType result) {
				// nada
			}

			@Override
			public void onFailure(Throwable t) {
				exceptionManager.onFailure(this, t);
			}

		});

		return this.resultCache;
	}

	public JsResultImplementation(ExceptionManager fallbackExceptionManager,
			AsyncResult<ResultType> asyncResult) {
		super();
		assert asyncResult != null;
		this.asyncResult = asyncResult;
		this.resultCache = null;
		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
		this.requestingResult = false;
		this.deferredCalls = new LinkedList<ResultCallback<ResultType>>();
	}

}
