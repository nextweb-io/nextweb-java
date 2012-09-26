package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.fn.SuccessFail;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.callbacks.EagerCallback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.async.joiner.CallbackLatch;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenCommitted;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithCommittedResult;

import com.ononedb.nextweb.internal.OnedbFactory;

public class OnedbSession implements Session {

	private final OnedbNextwebEngine engine;
	private final OneClient client;
	private final ExceptionManager exceptionManager;

	public OneClient getClient() {
		return client;
	}

	public OnedbFactory getFactory() {
		return getOnedbEngine().getFactory();
	}

	public OnedbNextwebEngine getOnedbEngine() {
		return this.engine;
	}

	@Override
	public NextwebEngine getEngine() {
		return getOnedbEngine();
	}

	public OnedbSession(OnedbNextwebEngine engine, OneClient client) {
		super();
		this.engine = engine;
		this.client = client;
		this.exceptionManager = engine.getFactory()
				.createExceptionManager(null);
	}

	@Override
	public Result<Success> commit() {
		Result<Success> commitResult = this.engine.createResult(
				exceptionManager, this, new AsyncResult<Success>() {

					@Override
					public void get(final Callback<Success> callback) {
						client.one().commit(client).and(new WhenCommitted() {

							@Override
							public void thenDo(WithCommittedResult arg0) {
								callback.onSuccess(Success.INSTANCE);
							}

							@Override
							public void onFailure(Throwable t) {
								callback.onFailure(Fn.exception(this, t));
							}

						});
					}
				});

		commitResult.get(new Closure<Success>() {

			@Override
			public void apply(Success o) {

			}
		});

		return commitResult;
	}

	@Override
	public Result<Success> close() {

		Result<Success> closeResult = this.engine.createResult(
				exceptionManager, this, new AsyncResult<Success>() {

					@Override
					public void get(final Callback<Success> callback) {

						client.runSafe(new Runnable() {

							@Override
							public void run() {
								client.one().commit(client)
										.and(new WhenCommitted() {

											@Override
											public void thenDo(
													WithCommittedResult r) {
												client.runSafe(new Runnable() {

													@Override
													public void run() {
														client.one()
																.shutdown(
																		client)
																.and(new WhenShutdown() {

																	@Override
																	public void thenDo() {
																		callback.onSuccess(Success.INSTANCE);
																	}

																	@Override
																	public void onFailure(
																			Throwable t) {
																		callback.onFailure(Fn
																				.exception(
																						this,
																						t));
																	}

																});
													}
												});

											}

											@Override
											public void onFailure(Throwable t) {
												callback.onFailure(Fn
														.exception(this, t));
											}

										});

							}
						});

					}

				});

		closeResult.get(new Closure<Success>() {

			@Override
			public void apply(Success o) {

			}

		});

		return closeResult;
	}

	@Override
	public Link node(String uri) {
		return engine.getFactory().createLink(this, null, uri, ""); // _NO_
																	// parent
		// exception
		// Manager
	}

	@Override
	public Link node(String uri, String secret) {

		return engine.getFactory().createLink(this, null, uri, secret);
	}

	@Override
	public Session getAll(Result<?>... results) {

		Result<SuccessFail> callback = getAll(true, results);

		SuccessFail result = callback.get();

		if (result == null) {
			return this;
		}

		if (result.isFail()) {
			throw new RuntimeException(result.getException());
		}

		return this;
	}

	@Override
	public Result<SuccessFail> getAll(final boolean asynchronous,
			final Result<?>... results) {
		Result<SuccessFail> getAllResult = engine.createResult(
				exceptionManager, this, new AsyncResult<SuccessFail>() {

					@SuppressWarnings({ "unchecked" })
					@Override
					public void get(final Callback<SuccessFail> callback) {

						final CallbackLatch latch = new CallbackLatch(
								results.length) {

							@Override
							public void onFailed(Throwable arg0) {
								callback.onSuccess(SuccessFail.fail(arg0));
							}

							@Override
							public void onCompleted() {
								callback.onSuccess(SuccessFail.success());
							}
						};

						Result<Object>[] resultObj = (Result<Object>[]) results;

						for (Result<Object> result : resultObj) {
							EagerCallback<Object> eagerCallback = CallbackFactory
									.eagerCallback(OnedbSession.this,
											exceptionManager,
											new Closure<Object>() {

												@Override
												public void apply(Object o) {
													latch.registerSuccess();
												}

											}).catchFailures(
											new ExceptionListener() {

												@Override
												public void onFailure(
														ExceptionResult r) {
													latch.registerFail(r
															.exception());
												}
											});
							result.get(eagerCallback);

						}

					}
				});

		return getAllResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <GType extends Session, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {

		return Plugins.plugin((GType) this, factory);
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

}
