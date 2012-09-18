package com.ononedb.nextweb.jre;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.ExceptionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import one.client.jre.OneJre;
import one.core.dsl.CoreDsl;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.common.OnedbFactory;

public class OnedbNextwebJreEngine implements OnedbNextwebEngine {

	private CoreDsl dsl;

	private final ExceptionManager exceptionManager;

	public static NextwebEngine init() {
		NextwebEngine engine = new OnedbNextwebJreEngine();
		Nextweb.injectEngine(engine);
		return engine;
	}

	@Override
	public Session createSession() {

		if (dsl == null) {
			dsl = OneJre.init();
		}

		return getFactory().createSession(this, exceptionManager,
				dsl.createClient());
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	@Override
	public <ResultType> Result<ResultType> createResult(
			final AsyncResult<ResultType> asyncResult) {

		return new Result<ResultType>() {

			ResultType cached = null;
			AtomicBoolean requesting = new AtomicBoolean();
			List<ResultCallback<ResultType>> deferredCalls = new LinkedList<ResultCallback<ResultType>>();
			ExceptionListener exceptionListener;

			@Override
			public synchronized void get(
					final ResultCallback<ResultType> callback) {
				if (cached != null) {
					callback.onSuccess(cached);
					return;
				}
				if (requesting.get()) {
					deferredCalls.add(callback);
					return;
				}
				requesting.set(true);

				asyncResult.get(new ResultCallback<ResultType>() {

					@Override
					public void onSuccess(ResultType result) {
						cached = result;
						requesting.set(false);
						callback.onSuccess(result);

						for (ResultCallback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onSuccess(result);
						}

					}

					@Override
					public void onFailure(Throwable t) {
						requesting.set(false);
						if (exceptionListener == null) {
							callback.onFailure(t);
							for (ResultCallback<ResultType> deferredCallback : deferredCalls) {
								deferredCallback.onFailure(t);
							}
							return;
						}
						exceptionListener.onFailure(this, t);
					}

				});
			}

			@Override
			public ResultType get() {

				final CountDownLatch latch = new CountDownLatch(2);

				final List<Throwable> exceptionList = Collections
						.synchronizedList(new ArrayList<Throwable>(1));

				get(new ResultCallback<ResultType>() {

					@Override
					public void onSuccess(ResultType result) {
						latch.countDown();
					}

					@Override
					public void onFailure(Throwable t) {
						exceptionList.add(t);
						latch.countDown();
					}

				});

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
			public void catchExceptions(ExceptionListener listener) {
				exceptionListener = listener;
			}

		};
	}

	@Override
	public OnedbFactory getFactory() {

		return new OnedbFactory();
	}

	public OnedbNextwebJreEngine() {
		super();
		this.exceptionManager = new ExceptionManager(null);
		this.exceptionManager.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				throw new RuntimeException("Uncaught background exception: "
						+ t.getMessage() + " from class: " + origin.getClass(),
						t);
			}
		});
	}

}
