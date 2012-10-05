package com.ononedb.nextweb.jre;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.ImpossibleListener;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenCommitted;
import one.core.dsl.callbacks.results.WithCommittedResult;

import com.ononedb.nextweb.OnedbSession;

public final class ResultImplementation<ResultType> implements
		Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;
	private final AtomicBoolean requesting;
	private final List<Callback<ResultType>> deferredCalls;
	private final ExceptionManager exceptionManager;
	private final Session session;

	ResultType cached = null;

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	public ResultImplementation(final Session session,
			final ExceptionManager exceptionManager,
			final AsyncResult<ResultType> asyncResult) {
		super();
		this.asyncResult = asyncResult;
		this.session = session;
		if (session != null) {
			this.exceptionManager = ((OnedbSession) session).getFactory()
					.createExceptionManager(exceptionManager);
		} else {
			this.exceptionManager = new ExceptionManager(exceptionManager);
		}
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
							public void apply(final ResultType result) {
								cached = result;
								requesting.set(false);
								callback.onSuccess(result);

								for (final Callback<ResultType> deferredCallback : deferredCalls) {
									deferredCallback.onSuccess(result);
								}
								deferredCalls.clear();
							}

						}).catchExceptions(new ExceptionListener() {

					@Override
					public void onFailure(final ExceptionResult r) {
						requesting.set(false);

						callback.onFailure(r);

						for (final Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onFailure(r);
						}
						deferredCalls.clear();
					}
				}).catchUndefined(new UndefinedListener() {

					@Override
					public void onUndefined(final UndefinedResult r) {
						requesting.set(false);

						callback.onUndefined(r);

						for (final Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onUndefined(r);
						}
						deferredCalls.clear();
					}
				}).catchUnauthorized(new UnauthorizedListener() {

					@Override
					public void onUnauthorized(final UnauthorizedResult r) {
						requesting.set(false);

						callback.onUnauthorized(r);

						for (final Callback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onUnauthorized(r);
						}
						deferredCalls.clear();
					}
				}));

		// execute commit upon every explicit get call ?!?
		if (session != null) {

			final OneClient client = ((OnedbSession) session).getClient();
			client.one().commit(client).and(new WhenCommitted() {

				@Override
				public void thenDo(final WithCommittedResult r) {

				}

				@Override
				public void onFailure(final Throwable t) {
					exceptionManager.onFailure(Fn.exception(this, t));
				}

			});
		}

	}

	@Override
	public ResultType get() {

		if (Thread.currentThread().getName()
				.startsWith("nx.remote.AccessThread")) {
			throw new IllegalStateException(
					"Cannot perform synchronous get() in engine worker thread.\n"
							+ "Please create your own Thread using new Thread(..).start(); to call synchronous get() or use"
							+ " asynchronous get(Callback) operation.");
		}

		final CountDownLatch latch = new CountDownLatch(2);

		final List<Throwable> exceptionList = Collections
				.synchronizedList(new ArrayList<Throwable>(1));

		get(CallbackFactory.eagerCallback(session, exceptionManager,
				new Closure<ResultType>() {

					@Override
					public void apply(final ResultType o) {
						// System.out.println("count down after " + o);
						latch.countDown();
					}
				}).catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(final ExceptionResult r) {
				exceptionList.add(r.exception());
				latch.countDown();
			}
		}));

		latch.countDown();
		try {
			latch.await();
		} catch (final InterruptedException e) {
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

	@Override
	public Result<ResultType> catchUnauthorized(
			final UnauthorizedListener listener) {
		this.exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public Result<ResultType> catchImpossible(final ImpossibleListener listener) {
		this.exceptionManager.catchImpossible(listener);
		return this;
	}

	@Override
	public Result<ResultType> catchUndefined(final UndefinedListener listener) {
		this.exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public Result<ResultType> catchExceptions(final ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

}