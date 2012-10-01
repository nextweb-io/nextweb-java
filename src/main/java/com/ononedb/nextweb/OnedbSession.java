package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
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
import one.core.dsl.callbacks.WhenRealmCreated;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithCommittedResult;
import one.core.dsl.callbacks.results.WithQuotaExceededContext;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.dsl.callbacks.results.WithSeedResult;

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

	public OnedbSession(final OnedbNextwebEngine engine, final OneClient client) {
		super();
		this.engine = engine;
		this.client = client;
		this.exceptionManager = engine.getFactory()
				.createExceptionManager(null);
	}

	@Override
	public Result<Success> commit() {
		final Result<Success> commitResult = this.engine.createResult(
				exceptionManager, this, new AsyncResult<Success>() {

					@Override
					public void get(final Callback<Success> callback) {
						client.one().commit(client).and(new WhenCommitted() {

							@Override
							public void thenDo(final WithCommittedResult arg0) {
								callback.onSuccess(Success.INSTANCE);
							}

							@Override
							public void onFailure(final Throwable t) {
								callback.onFailure(Fn.exception(this, t));
							}

						});
					}
				});

		commitResult.get(new Closure<Success>() {

			@Override
			public void apply(final Success o) {

			}
		});

		return commitResult;
	}

	@Override
	public Query seed(final String seedType) {
		final AsyncResult<Node> seedResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				client.runSafe(new Runnable() {

					@Override
					public void run() {

						client.one().seed(client, seedType, new WhenSeeded() {

							@Override
							public void thenDo(final WithSeedResult r) {

								callback.onSuccess(engine.getFactory()
										.createNode(OnedbSession.this,
												exceptionManager, r.seedNode(),
												r.accessToken()));

							}

							@Override
							public void onFailure(final Throwable t) {
								callback.onFailure(Fn.exception(this, t));
							}

						});

					}
				});

			}
		};
		return this.engine.getFactory().createQuery(this, exceptionManager,
				seedResult);
	}

	@Override
	public Query seed() {
		return seed("default");
	}

	@Override
	public Query createRealm(final String realmTitle, final String realmType,
			final String apiKey) {
		final AsyncResult<Node> createRealmResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				client.runSafe(new Runnable() {

					@Override
					public void run() {
						client.one().createRealm(realmTitle)
								.withType(realmType).withApiKey(apiKey)
								.in(client).and(new WhenRealmCreated() {

									@Override
									public void thenDo(
											final WithRealmCreatedResult cr) {
										callback.onSuccess(engine.getFactory()
												.createNode(
														OnedbSession.this,
														exceptionManager,
														client.one().reference(
																cr.root()),
														cr.secret()));
									}

									@Override
									public void onQuotaExceeded(
											final WithQuotaExceededContext context) {
										callback.onFailure(Fn
												.exception(
														this,
														new Exception(
																"Cannot create realm because quota for API key is exceeded.\n  Message: "
																		+ context
																				.message())));
									}

									@Override
									public void onFailure(final Throwable t) {
										callback.onFailure(Fn
												.exception(this, t));
									}

								});
					}
				});

			}
		};
		return this.engine.getFactory().createQuery(this, exceptionManager,
				createRealmResult);
	}

	@Override
	public Result<Success> close() {

		final Result<Success> closeResult = this.engine.createResult(
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
													final WithCommittedResult r) {
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
																			final Throwable t) {
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
											public void onFailure(
													final Throwable t) {
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
			public void apply(final Success o) {

			}

		});

		return closeResult;
	}

	@Override
	public Link node(final String uri) {
		return engine.getFactory().createLink(this, null, uri, ""); // _NO_
																	// parent
		// exception
		// Manager
	}

	@Override
	public Link node(final String uri, final String secret) {

		return engine.getFactory().createLink(this, null, uri, secret);
	}

	@Override
	public Session getAll(final Result<?>... results) {

		final Result<SuccessFail> callback = getAll(true, results);

		client.one().commit(client).and(new WhenCommitted() {

			@Override
			public void thenDo(final WithCommittedResult r) {

			}

			@Override
			public void onFailure(final Throwable t) {
				exceptionManager.onFailure(Fn.exception(this, t));
			}

		});

		final SuccessFail result = callback.get();

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
		final Result<SuccessFail> getAllResult = engine.createResult(
				exceptionManager, this, new AsyncResult<SuccessFail>() {

					@SuppressWarnings({ "unchecked" })
					@Override
					public void get(final Callback<SuccessFail> callback) {

						final CallbackLatch latch = new CallbackLatch(
								results.length) {

							@Override
							public void onFailed(final Throwable arg0) {
								callback.onSuccess(SuccessFail.fail(arg0));
							}

							@Override
							public void onCompleted() {
								callback.onSuccess(SuccessFail.success());
							}
						};

						final Result<Object>[] resultObj = (Result<Object>[]) results;

						for (final Result<Object> result : resultObj) {
							final EagerCallback<Object> eagerCallback = CallbackFactory
									.eagerCallback(OnedbSession.this,
											exceptionManager,
											new Closure<Object>() {

												@Override
												public void apply(final Object o) {
													latch.registerSuccess();
												}

											}).catchExceptions(
											new ExceptionListener() {

												@Override
												public void onFailure(
														final ExceptionResult r) {
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

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

}
