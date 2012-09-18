package com.ononedb.nextweb;

import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.fn.SuccessFail;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenShutdown;

import com.ononedb.nextweb.common.OnedbFactory;

public class OnedbSession implements Session {

	private final OnedbNextwebEngine engine;
	private final OneClient client;
	private final ExceptionManager exceptionManager;

	@Override
	public Query load(String uri) {

		return null;

	}

	public OneClient getClient() {
		return client;
	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
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

		return this.engine.createResult(new AsyncResult<SuccessFail>() {

			@Override
			public void get(final ResultCallback<SuccessFail> callback) {

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
	}

}
