package com.ononedb.nextweb;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Monitor;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.common.Interval;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.BooleanResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Fn;
import io.nextweb.fn.IntegerResult;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.ImpossibleListener;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import io.nextweb.plugins.core.Plugin_Entity_Append;
import io.nextweb.plugins.core.Plugin_Entity_ClearVersions;
import io.nextweb.plugins.core.Plugin_Entity_Monitor;
import io.nextweb.plugins.core.Plugin_Entity_Remove;
import io.nextweb.plugins.core.Plugin_Entity_Select;
import io.nextweb.plugins.core.Plugin_Entity_SetValue;
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

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
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
	public String getSecret() {
		return secret;
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
									public void thenDo(
											final WithLoadResult<Object> lr) {
										callback.onSuccess(session
												.getOnedbEngine()
												.getFactory()
												.createNode(session,
														exceptionManager,
														lr.loadedNode(), secret));
									}

									@Override
									public void onUnauthorized(
											final WithUnauthorizedContext context) {
										callback.onUnauthorized(H
												.fromUnauthorizedContext(this,
														context));
									}

									@Override
									public void onUndefined(
											final WithUndefinedContext context) {
										callback.onUndefined(H
												.createUndefinedResult(this,
														uri));

									}

									@Override
									public void onFailure(final Throwable t) {
										callback.onFailure(Fn
												.exception(this, t));
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
	public void get(final Callback<Node> callback) {
		assert this.result != null;

		this.result.get(callback);
	}

	@Override
	public Link catchExceptions(final ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public Link catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public Link catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public Link catchImpossible(final ImpossibleListener listener) {
		exceptionManager.catchImpossible(listener);
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
	public LinkList node(final Link link) {

		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public String toString() {
		return "link(\"" + this.getUri() + "\")";
	}

	@Override
	public void get(final Closure<Node> callback) {
		result.get(callback);
	}

	/**
	 * Plugins ...
	 */

	@Override
	public Query select(final Link propertyType) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Select<OnedbEntity>> select = H
				.plugins(session).select();
		return plugin(select).select(propertyType);
	}

	@Override
	public ListQuery selectAll(final Link propertyType) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Select<OnedbEntity>> select = H
				.plugins(session).select();
		return plugin(select).selectAll(propertyType);
	}

	@Override
	public LinkListQuery selectAllLinks() {
		final PluginFactory<OnedbEntity, Plugin_Entity_Select<OnedbEntity>> select = H
				.plugins(session).select();
		return plugin(select).selectAllLinks();
	}

	@Override
	public ListQuery selectAll() {
		final PluginFactory<OnedbEntity, Plugin_Entity_Select<OnedbEntity>> select = H
				.plugins(session).select();
		return plugin(select).selectAll();
	}

	@Override
	public BooleanResult has(final Link propertyType) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Select<OnedbEntity>> select = H
				.plugins(session).select();
		return plugin(select).has(propertyType);
	}

	@Override
	public Result<Success> remove(final Entity entity) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Remove<OnedbEntity>> remove = H
				.plugins(session).remove();
		return plugin(remove).remove(entity);
	}

	@Override
	public Query append(final Object value) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).append(value);
	}

	@Override
	public Query append(final Object value, final String atAddress) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).append(value, atAddress);
	}

	@Override
	public Query appendValue(final Object value) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).appendValue(value);
	}

	@Override
	public Query append(final Entity entity) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).append(entity);
	}

	@Override
	public Query appendSafe(final Object value) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).appendSafe(value);
	}

	@Override
	public Query appendSafe(final Object value, final String atAddress) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).appendSafe(value, atAddress);
	}

	@Override
	public Query appendValueSafe(final Object value) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).appendValueSafe(value);
	}

	@Override
	public Query appendSafe(final Entity entity) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).appendSafe(entity);
	}

	@Override
	public Query insert(final Object value, final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insert(value, atIndex);
	}

	@Override
	public Query insert(final Object value, final String atAddress,
			final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insert(value, atAddress, atIndex);
	}

	@Override
	public Query insertValue(final Object value, final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insertValue(value, atIndex);
	}

	@Override
	public Query insert(final Entity entity, final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insertValue(entity, atIndex);
	}

	@Override
	public Query insertSafe(final Object value, final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insertSafe(value, atIndex);
	}

	@Override
	public Query insertSafe(final Object value, final String atAddress,
			final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insertSafe(value, atAddress, atIndex);
	}

	@Override
	public Query insertValueSafe(final Object value, final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insertValueSafe(value, atIndex);
	}

	@Override
	public Query insertSafe(final Entity entity, final int atIndex) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Append<OnedbEntity>> append = H
				.plugins(session).append();
		return plugin(append).insertSafe(entity, atIndex);
	}

	@Override
	public Query setValue(final Object newValue) {
		final PluginFactory<OnedbEntity, Plugin_Entity_SetValue<OnedbEntity>> setValue = H
				.plugins(session).setValue();
		return plugin(setValue).setValue(newValue);
	}

	@Override
	public Query setValueSafe(final Object newValue) {
		final PluginFactory<OnedbEntity, Plugin_Entity_SetValue<OnedbEntity>> setValue = H
				.plugins(session).setValue();
		return plugin(setValue).setValueSafe(newValue);
	}

	@Override
	public IntegerResult clearVersions(final int keepVersions) {
		final PluginFactory<OnedbEntity, Plugin_Entity_ClearVersions<OnedbEntity>> clearVersions = H
				.plugins(session).clearVersions();
		return plugin(clearVersions).clearVersions(keepVersions);
	}

	@Override
	public Result<Monitor> monitor(final Interval interval,
			final Closure<Node> whenChanged) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Monitor<OnedbEntity>> monitor = H
				.plugins(session).monitor();
		return plugin(monitor).monitor(interval, whenChanged);
	}

}
