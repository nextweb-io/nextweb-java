package com.ononedb.nextweb.js.fn;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;

import java.util.LinkedList;
import java.util.List;

public class JsResultImplementation<ResultType> implements Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;

	private ResultType resultCache;

	private final ExceptionManager exceptionManager;

	private boolean requestingResult;

	private final List<Callback<ResultType>> deferredCalls;

	private final Session session;

	private void requestResult(final Callback<ResultType> callback) {

		if (resultCache != null) {
			callback.onSuccess(resultCache);
			return;
		}

		if (requestingResult) {
			deferredCalls.add(callback);
			return;
		}

		requestingResult = true;

		// GWT.log("Requestion result for" + asyncResult);

		asyncResult.get(CallbackFactory
				.eagerCallback(session, exceptionManager,
						new Closure<ResultType>() {

							@Override
							public void apply(ResultType result) {
								resultCache = result;
								requestingResult = false;
								callback.onSuccess(result);

								for (Callback<ResultType> deferredCallback : deferredCalls) {
									deferredCallback.onSuccess(result);
								}
								deferredCalls.clear();
							}

						})
				.catchFailures(new ExceptionListener() {

					@Override
					public void onFailure(Object origin, Throwable t) {
						requestingResult = false;
						callback.onFailure(origin, t);
						for (Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onFailure(origin, t);
						}
						deferredCalls.clear();
					}
				})
				.catchAuthorizationExceptions(
						new AuthorizationExceptionListener() {

							@Override
							public void onUnauthorized(Object origin,
									AuthorizationExceptionResult r) {
								requestingResult = false;
								callback.onUnauthorized(origin, r);
								for (Callback<ResultType> deferredCallback : deferredCalls) {
									deferredCallback.onUnauthorized(origin, r);
								}
								deferredCalls.clear();
							}
						})
				.catchUndefinedExceptions(new UndefinedExceptionListener() {

					@Override
					public void onUndefined(Object origin, String message) {
						requestingResult = false;
						callback.onUndefined(origin, message);
						for (Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onUndefined(origin, message);
						}
						deferredCalls.clear();
					}
				}));

	}

	@Override
	public void get(final Callback<ResultType> callback) {
		requestResult(callback);
	}

	/**
	 * Will trigger a request
	 */
	@Override
	public ResultType get() {

		requestResult(CallbackFactory.lazyCallback(session, exceptionManager,
				new Closure<ResultType>() {

					@Override
					public void apply(ResultType o) {

					}
				}));

		return this.resultCache;
	}

	public JsResultImplementation(Session session,
			ExceptionManager fallbackExceptionManager,
			AsyncResult<ResultType> asyncResult) {
		super();
		assert asyncResult != null;
		this.session = session;
		this.asyncResult = asyncResult;
		this.resultCache = null;
		this.exceptionManager = fallbackExceptionManager;
		this.requestingResult = false;
		this.deferredCalls = new LinkedList<Callback<ResultType>>();
	}

	@Override
	public void get(final Closure<ResultType> callback) {
		get(CallbackFactory.lazyCallback(session, exceptionManager,
				new Closure<ResultType>() {

					@Override
					public void apply(ResultType o) {
						callback.apply(o);
					}
				}));

	}
}
