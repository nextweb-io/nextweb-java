package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.fn.RequestResultCallback;
import io.nextweb.fn.SuccessFail;
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
		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
	}

	@Override
	public Result<SuccessFail> close() {

		Result<SuccessFail> closeResult = this.engine
				.createResult(new AsyncResult<SuccessFail>() {

					@Override
					public void get(final RequestResultCallback<SuccessFail> callback) {

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

		RequestResultCallback<SuccessFail> clbk = RequestResultCallback.doNothing();
		closeResult.get(clbk);

		return closeResult;
	}

	@Override
	public Link node(String uri) {
		return engine.getFactory().createLink(this, exceptionManager, uri);
	}

	@Override
	public Result<SuccessFail> getAll(final Result<?>... results) {
		return engine.createResult(new AsyncResult<SuccessFail>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void get(final RequestResultCallback<SuccessFail> callback) {

				final CallbackLatch latch = new CallbackLatch(results.length) {

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
					result.get(new RequestResultCallback() {

						@Override
						public void onSuccess(Object result) {
							latch.registerSuccess();
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

}
