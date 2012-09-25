package com.ononedb.nextweb;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
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

public class OnedbLink implements Link, OnedbEntity {

	private final OnedbSession session;
	private final String uri;
	private final Result<Node> result;
	private final ExceptionManager exceptionManager;
	private final String secret;

	@SuppressWarnings("unchecked")
	@Override
	public <GType extends Entity, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {

		return Plugins.plugin((GType) this, factory);
	}

	@Override
	public Query select(Link propertyType) {
		return plugin(H.plugins(session).select()).select(propertyType);
	}

	@Override
	public NodeListQuery selectAll(Link propertyType) {
		return plugin(H.plugins(session).select()).selectAll(propertyType);
	}

	@Override
	public LinkListQuery selectAllLinks() {
		return plugin(H.plugins(session).select()).selectAllLinks();
	}

	@Override
	public NodeListQuery selectAll() {
		return plugin(H.plugins(session).select()).selectAll();
	}

	@Override
	public Result<Success> remove(Entity entity) {
		return plugin(H.plugins(session).remove()).remove(entity);
	}

	@Override
	public Query append(Object value) {

		return plugin(H.plugins(session).append()).append(value);
	}

	@Override
	public Query append(Object value, String atAddress) {
		return plugin(H.plugins(session).append()).append(value, atAddress);
	}

	@Override
	public Query appendValue(Object value) {
		return plugin(H.plugins(session).append()).appendValue(value);
	}

	@Override
	public <GEntity extends Entity> GEntity append(GEntity entity) {

		return plugin(H.plugins(session).append()).append(entity);
	}

	@Override
	public Query setValue(Object newValue) {
		return plugin(H.plugins(session).setValue()).setValue(newValue);
	}

	@Override
	public Query setValueSafe(Object newValue) {
		return plugin(H.plugins(session).setValue()).setValueSafe(newValue);
	}

	@Override
	public Result<Integer> clearVersions(int keepVersions) {
		return plugin(H.plugins(session).clearVersions()).clearVersions(
				keepVersions);
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
			final ExceptionManager parentExceptionManager, final String uri,
			final String secret) {
		super();
		assert session != null;
		assert uri != null;

		this.session = session;
		this.uri = uri;
		this.secret = secret;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
		this.result = session.getEngine().createResult(exceptionManager,
				session, new AsyncResult<Node>() {

					@Override
					public void get(final Callback<Node> callback) {

						session.getClient().one().load(uri).withSecret(secret)
								.in(session.getClient()).and(new WhenLoaded() {

									@Override
									public void thenDo(WithLoadResult<Object> lr) {
										callback.onSuccess(session
												.getOnedbEngine()
												.getFactory()
												.createNode(session,
														exceptionManager,
														lr.loadedNode()));
									}

									@Override
									public void onUnauthorized(
											WithUnauthorizedContext context) {
										callback.onUnauthorized(
												this,
												H.fromUnauthorizedContext(context));
									}

									@Override
									public void onUndefined(
											WithUndefinedContext context) {
										callback.onUndefined(this,
												"No node is defined at address: ["
														+ uri + "]");
									}

									@Override
									public void onFailure(Throwable t) {
										callback.onFailure(this, t);
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
	public void get(Callback<Node> callback) {
		assert this.result != null;

		this.result.get(callback);
	}

	@Override
	public Link catchExceptions(ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public Link catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		exceptionManager.catchAuthorizationExceptions(listener);
		return this;
	}

	@Override
	public Link catchUndefinedExceptions(UndefinedExceptionListener listener) {
		exceptionManager.catchUndefinedExceptions(listener);
		return this;
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

	@Override
	public String toString() {
		return "link(\"" + this.getUri() + "\")";
	}

	@Override
	public void get(Closure<Node> callback) {
		result.get(callback);
	}

}
