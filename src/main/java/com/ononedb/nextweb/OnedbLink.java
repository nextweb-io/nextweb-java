package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.core.dsl.callbacks.WhenLoaded;
import one.core.dsl.callbacks.results.WithLoadResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.dsl.callbacks.results.WithUndefinedContext;

import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.plugins.EntityPlugin_Select;

public class OnedbLink implements Link, OnedbEntity {

	private final OnedbSession session;
	private final String uri;
	private final Result<Node> result;
	private final ExceptionManager exceptionManager;

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {

		return Plugins.plugin(this, factory);
	}

	@Override
	public Query select(Link propertyType) {
		return new EntityPlugin_Select(this).select(propertyType);
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public String getUri() {

		return uri;
	}

	@Override
	public String uri() {
		return getUri();
	}

	public OnedbLink(final OnedbSession session,
			final ExceptionManager fallbackExceptionManager, final String uri) {
		super();
		assert session != null;
		assert uri != null;

		this.session = session;
		this.uri = uri;
		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
		this.result = session.getEngine().createResult(new AsyncResult<Node>() {

			@Override
			public void get(final ResultCallback<Node> callback) {

				session.getClient().one().load(uri).in(session.getClient())
						.and(new WhenLoaded() {

							@Override
							public void thenDo(WithLoadResult<Object> lr) {
								callback.onSuccess(session
										.getOnedbEngine()
										.getFactory()
										.createNode(session, exceptionManager,
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
		assert this.result != null;
	}

	@Override
	public Node get() {

		return this.result.get();
	}

	@Override
	public void get(ResultCallback<Node> callback) {
		assert this.result != null;

		this.result.get(callback);
	}

	@Override
	public void catchExceptions(ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
	}

	@Override
	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		exceptionManager.catchAuthorizationExceptions(listener);
	}

	@Override
	public void catchUndefinedExceptions(UndefinedExceptionListener listener) {
		exceptionManager.catchUndefinedExceptions(listener);
	}

	@Override
	public Session getSession() {

		return session;
	}

	@Override
	public ExceptionManager getExceptionManager() {

		return this.exceptionManager;
	}

	@Override
	public LinkList node(Link link) {

		throw new RuntimeException("Not implemented yet");
	}

}
