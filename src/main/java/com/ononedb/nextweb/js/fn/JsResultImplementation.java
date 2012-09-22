package com.ononedb.nextweb.js.fn;

import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.fn.RequestResultCallback;
import io.nextweb.operations.exceptions.ExceptionManager;

import java.util.LinkedList;
import java.util.List;

public class JsResultImplementation<ResultType> implements Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;

	private ResultType resultCache;

	private final ExceptionManager exceptionManager;

	private boolean requestingResult;

	private final List<RequestResultCallback<ResultType>> deferredCalls;

	private void requestResult(final RequestResultCallback<ResultType> callback) {

		if (resultCache != null) {
			callback.onSuccess(resultCache);
			return;
		}

		if (requestingResult) {
			deferredCalls.add(callback);
			return;
		}

		asyncResult.get(new RequestResultCallback<ResultType>() {

			@Override
			public void onSuccess(ResultType result) {
				resultCache = result;
				requestingResult = false;
				callback.onSuccess(result);

				for (RequestResultCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onSuccess(result);
				}
				deferredCalls.clear();
			}

			@Override
			public void onFailure(Throwable t) {
				requestingResult = false;
				callback.onFailure(t);
				for (RequestResultCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onFailure(t);
				}
				deferredCalls.clear();

			}

		});

	}

	@Override
	public void get(final RequestResultCallback<ResultType> callback) {
		requestResult(callback);
	}

	/**
	 * Will trigger a request
	 */
	@Override
	public ResultType get() {

		requestResult(new RequestResultCallback<ResultType>() {

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
		this.deferredCalls = new LinkedList<RequestResultCallback<ResultType>>();
	}

}
