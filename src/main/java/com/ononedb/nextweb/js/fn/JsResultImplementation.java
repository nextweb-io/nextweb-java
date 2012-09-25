package com.ononedb.nextweb.js.fn;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;

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

						}).catchFailures(new ExceptionListener() {

					@Override
					public void onFailure(Object origin, Throwable t) {
						requestingResult = false;
						callback.onFailure(origin, t);
						for (Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onFailure(origin, t);
						}
						deferredCalls.clear();
					}
				}).catchAuthorizationExceptions(new UnauthorizedListener() {

					@Override
					public void onUnauthorized(Object origin,
							UnauthorizedResult r) {
						requestingResult = false;
						callback.onUnauthorized(origin, r);
						for (Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onUnauthorized(origin, r);
						}
						deferredCalls.clear();
					}
				}).catchUndefinedExceptions(new UndefinedListener() {

					@Override
					public void onUndefined(UndefinedResult r) {
						requestingResult = false;
						callback.onUndefined(r);
						for (Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onUndefined(r);
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
