package com.ononedb.nextweb.js.fn;

import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.RequestCallback;
import io.nextweb.fn.RequestCallbackImpl;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;

import java.util.LinkedList;
import java.util.List;

public class JsResultImplementation<ResultType> implements Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;

	private ResultType resultCache;

	private final ExceptionManager exceptionManager;

	private boolean requestingResult;

	private final List<RequestCallback<ResultType>> deferredCalls;

	private void requestResult(final RequestCallback<ResultType> callback) {

		if (resultCache != null) {
			callback.onSuccess(resultCache);
			return;
		}

		if (requestingResult) {
			deferredCalls.add(callback);
			return;
		}

		asyncResult.get(new RequestCallbackImpl<ResultType>(exceptionManager,
				null) {

			@Override
			public void onSuccess(ResultType result) {
				resultCache = result;
				requestingResult = false;
				callback.onSuccess(result);

				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onSuccess(result);
				}
				deferredCalls.clear();
			}

			@Override
			public void onFailure(Object origin, Throwable t) {
				requestingResult = false;
				callback.onFailure(origin, t);
				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onFailure(origin, t);
				}
				deferredCalls.clear();

			}

			@Override
			public void onUnauthorized(Object origin,
					AuthorizationExceptionResult r) {
				requestingResult = false;
				callback.onUnauthorized(origin, r);
				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onUnauthorized(origin, r);
				}
				deferredCalls.clear();
			}

			@Override
			public void onUndefined(Object origin, String message) {
				requestingResult = false;
				callback.onUndefined(origin, message);
				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onUndefined(origin, message);
				}
				deferredCalls.clear();
			}

		});

	}

	@Override
	public void get(final RequestCallback<ResultType> callback) {
		requestResult(callback);
	}

	/**
	 * Will trigger a request
	 */
	@Override
	public ResultType get() {

		requestResult(new RequestCallbackImpl<ResultType>(exceptionManager,
				null) {

			@Override
			public void onSuccess(ResultType result) {
				// nada
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
		this.exceptionManager = fallbackExceptionManager;
		this.requestingResult = false;
		this.deferredCalls = new LinkedList<RequestCallback<ResultType>>();
	}

	@Override
	public void get(final Closure<ResultType> callback) {
		get(new RequestCallbackImpl<ResultType>(exceptionManager, null) {

			@Override
			public void onSuccess(ResultType result) {
				callback.apply(result);
			}
		});
	}

}
