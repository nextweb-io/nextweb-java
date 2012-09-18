package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Node;
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
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenLoaded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithLoadResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.dsl.callbacks.results.WithUndefinedContext;

import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.common.OnedbFactory;

public class OnedbSession implements Session {

	private final OnedbNextwebEngine engine;
	private final OneClient client;
	private final ExceptionManager exceptionManager;

	@Override
	public Query load(final String uri) {

		final CoreDsl dsl = client.one();

		return engine.getFactory().createQuery(this, exceptionManager,
				new AsyncResult<Node>() {

					@Override
					public void get(final ResultCallback<Node> callback) {

						dsl.load(uri).in(client).and(new WhenLoaded() {

							@Override
							public void thenDo(WithLoadResult<Object> lr) {
								callback.onSuccess(engine.getFactory()
										.createNode(OnedbSession.this,
												exceptionManager,
												lr.loadedNode()));
							}

							@Override
							public void onUnauthorized(
									WithUnauthorizedContext context) {
								exceptionManager.onUnauthorized(this,
										H.fromUnauthorizedContext(context));
							}

							@Override
							public void onUndefined(WithUndefinedContext context) {
								exceptionManager.onUndefined(this);
							}

							@Override
							public void onFailure(Throwable t) {
								exceptionManager.onFailure(this, t);
							}

						});

					}
				});

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

	@Override
	public Link link(String uri) {

		return null;
	}

}
