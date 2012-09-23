package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.RequestCallback;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.async.joiner.CallbackLatch;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenShutdown;

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

	public OnedbSession(OnedbNextwebEngine engine,
			ExceptionManager fallbackExceptionManager, OneClient client) {
		super();
		this.engine = engine;
		this.client = client;
		this.exceptionManager = engine.getFactory()
				.createExceptionManager(null);
	}

	@Override
	public Result<SuccessFail> close() {

		Result<SuccessFail> closeResult = this.engine.createResult(
				exceptionManager, new AsyncResult<SuccessFail>() {

					@Override
					public void get(final RequestCallback<SuccessFail> callback) {

						client.one().shutdown(client).and(new WhenShutdown() {

							@Override
							public void thenDo() {
								callback.onSuccess(SuccessFail.success());
							}

							@Override
							public void onFailure(Throwable t) {
								callback.onSuccess(SuccessFail.fail(t));
							}

						});

					}

				});

		closeResult.get(new Closure<SuccessFail>() {

			@Override
			public void apply(SuccessFail o) {
				// nothing
			}

		});

		return closeResult;
	}

	@Override
	public Link node(String uri) {
		return engine.getFactory().createLink(this, null, uri); // _NO_ parent
																// exception
																// Manager
	}

	@Override
	public Result<SuccessFail> getAll(final Result<?>... results) {
		return engine.createResult(exceptionManager,
				new AsyncResult<SuccessFail>() {

					@SuppressWarnings({ "rawtypes", "unchecked" })
					@Override
					public void get(final RequestCallback<SuccessFail> callback) {

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

						for (Result<?> result : results) {
							result.get(new RequestCallbackImpl(
									exceptionManager, null) {

								@Override
								public void onSuccess(Object result) {
									latch.registerSuccess();
								}

								@Override
								public void onUnauthorized(Object origin,
										AuthorizationExceptionResult r) {
									latch.registerFail(new Exception(
											"Authorization exception: "
													+ r.getMessage()));
								}

								@Override
								public void onUndefined(Object origin,
										String message) {

									latch.registerFail(new Exception(
											"Node was not defined: " + message));
								}

								@Override
								public void onFailure(Object origin, Throwable t) {
									latch.registerFail(t);
								}

							});
						}

					}
				});
	}

	@Override
	public <PluginType extends Plugin<Session>> PluginType plugin(
			PluginFactory<Session, PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

}
