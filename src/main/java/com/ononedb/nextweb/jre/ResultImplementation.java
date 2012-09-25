package com.ononedb.nextweb.jre;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ResultImplementation<ResultType> implements
		Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;
	private final AtomicBoolean requesting;
	private final List<Callback<ResultType>> deferredCalls;
	private final ExceptionManager exceptionManager;
	private final Session session;

	ResultType cached = null;

	public ResultImplementation(Session session,
			ExceptionManager exceptionManager,
			AsyncResult<ResultType> asyncResult) {
		super();
		this.asyncResult = asyncResult;
		this.session = session;
		this.exceptionManager = exceptionManager;
		this.deferredCalls = new LinkedList<Callback<ResultType>>();
		requesting = new AtomicBoolean();
	}

	@Override
	public synchronized void get(final Callback<ResultType> callback) {
		if (cached != null) {
			callback.onSuccess(cached);
			return;
		}
		if (requesting.get()) {
			deferredCalls.add(callback);
			return;
		}
		requesting.set(true);

		asyncResult.get(CallbackFactory
				.eagerCallback(session, exceptionManager,
						new Closure<ResultType>() {

							@Override
							public void apply(ResultType result) {
								cached = result;
								requesting.set(false);
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
						requesting.set(false);

						callback.onFailure(origin, t);

						for (Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onFailure(origin, t);
						}
						deferredCalls.clear();
					}
				})
				.catchUndefinedExceptions(new UndefinedListener() {

					@Override
					public void onUndefined(Object origin, String message) {
						requesting.set(false);

						callback.onUndefined(origin, message);

						for (Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onUndefined(origin, message);
						}
						deferredCalls.clear();
					}
				})
				.catchAuthorizationExceptions(
						new UnauthorizedListener() {

							@Override
							public void onUnauthorized(Object origin,
									UnauthorizedResult r) {
								requesting.set(false);

								callback.onUnauthorized(origin, r);

								for (Callback<ResultType> deferredCallback : deferredCalls) {
									deferredCallback.onUnauthorized(origin, r);
								}
								deferredCalls.clear();
							}
						}));

	}

	@Override
	public ResultType get() {

		final CountDownLatch latch = new CountDownLatch(2);

		final List<Throwable> exceptionList = Collections
				.synchronizedList(new ArrayList<Throwable>(1));

		get(CallbackFactory.eagerCallback(session, exceptionManager,
				new Closure<ResultType>() {

					@Override
					public void apply(ResultType o) {
						// System.out.println("count down after " + o);
						latch.countDown();
					}
				}).catchFailures(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				exceptionList.add(t);
				latch.countDown();
			}
		}));

		latch.countDown();
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		if (exceptionList.size() > 0) {
			throw new RuntimeException(
					"Get call could not be completed successfully. An exception was thrown.",
					exceptionList.get(0));
		}

		assert cached != null;

		return cached;
	}

	@Override
	public void get(final Closure<ResultType> callback) {
		get(CallbackFactory.lazyCallback(session, exceptionManager, callback));
	}

}