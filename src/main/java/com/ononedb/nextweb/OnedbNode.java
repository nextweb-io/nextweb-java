package com.ononedb.nextweb;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.common.Interval;
import io.nextweb.common.Monitor;
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
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenLoaded;
import one.core.dsl.callbacks.results.WithLoadResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.dsl.callbacks.results.WithUndefinedContext;
import one.core.nodes.OneTypedReference;
import one.core.nodes.OneValue;

import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.plugins.Plugin_Entity_Append;
import com.ononedb.nextweb.plugins.Plugin_Entity_ClearVersions;
import com.ononedb.nextweb.plugins.Plugin_Entity_Monitor;
import com.ononedb.nextweb.plugins.Plugin_Entity_Remove;
import com.ononedb.nextweb.plugins.Plugin_Entity_Select;
import com.ononedb.nextweb.plugins.Plugin_Entity_SetValue;

public class OnedbNode implements Node, OnedbEntity {

	private final OnedbSession session;
	private final OneTypedReference<?> node;
	private final ExceptionManager exceptionManager;
	private final String secret;

	@Override
	public String getUri() {

		return node.getId();
	}

	@Override
	public String uri() {

		return getUri();
	}

	@Override
	public String getSecret() {
		return this.secret;
	}

	@Override
	public String secret() {
		return getSecret();
	}

	@Override
	public Object value() {

		return getValue();
	}

	@Override
	public boolean exists() {
		return H.dsl(this).isLoaded(node).in(session.getClient());
	}

	@Override
	public Object getValue() {
		final Object dereferenced = H.dsl(this).dereference(node)
				.in(session.getClient());

		if (dereferenced instanceof OneValue<?>) {
			return ((OneValue<?>) dereferenced).getValue();
		}

		if (dereferenced instanceof OneTypedReference<?>) {
			return session.node(((OneTypedReference<?>) dereferenced).getId());
		}

		return dereferenced;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ValueType> ValueType value(final Class<ValueType> type) {
		final Object value = getValue();

		if (type.equals(String.class)) {
			return (ValueType) value.toString();
		}

		if (value instanceof String && type.equals(Integer.class)) {
			return (ValueType) Integer.valueOf((String) value);
		}

		return (ValueType) value;
	}

	@Override
	public <Type> Type as(final Class<Type> type) {
		return value(type);
	}

	@Override
	public Query reload() {
		final AsyncResult<Node> reloadResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				final CoreDsl dsl = H.dsl(OnedbNode.this);

				dsl.reload(getUri()).withSecret(getSecret())
						.in(H.client(OnedbNode.this)).and(new WhenLoaded() {

							@Override
							public void thenDo(final WithLoadResult<Object> r) {
								callback.onSuccess(session.getFactory()
										.createNode(session, exceptionManager,
												r.loadedNode(), getSecret()));
							}

							@Override
							public void onUndefined(
									final WithUndefinedContext context) {
								callback.onUndefined(H.createUndefinedResult(
										this, getUri()));
							}

							@Override
							public void onUnauthorized(
									final WithUnauthorizedContext context) {
								callback.onUnauthorized(H
										.fromUnauthorizedContext(this, context));
							}

							@Override
							public void onFailure(final Throwable t) {
								callback.onFailure(Fn.exception(this, t));
							}

						});

			}
		};
		final OnedbQuery reloadQuery = session.getFactory().createQuery(
				session, exceptionManager, reloadResult);

		reloadQuery.get(new Closure<Node>() {

			@Override
			public void apply(final Node o) {
				// nothing
			}
		});

		return reloadQuery;
	}

	@Override
	public OnedbSession getOnedbSession() {
		return session;
	}

	public OnedbNode(final OnedbSession session,
			final ExceptionManager parentExceptionManager,
			final OneTypedReference<?> node, final String secret) {
		super();
		this.session = session;
		this.node = node;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
		this.secret = secret;
	}

	@Override
	public Node get() {
		return this;
	}

	@Override
	public void get(final Callback<Node> callback) {
		callback.onSuccess(this);
	}

	@Override
	public Node catchExceptions(final ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
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
	public String toString() {
		return "node(\"" + this.getUri() + "\", " + this.getValue().getClass()
				+ ")";
	}

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public Node catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public Node catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public Node catchImpossible(final ImpossibleListener listener) {
		exceptionManager.catchImpossible(listener);
		return this;
	}

	@Override
	public void get(final Closure<Node> callback) {
		callback.apply(this);
	}

	/**
	 * Plugins
	 */

	@Override
	public Query select(final Link propertyType) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Select<OnedbEntity>> select = H
				.plugins(session).select();
		return plugin(select).select(propertyType);
	}

	@Override
	public Query ifHas(final Link propertyType) {
		final PluginFactory<OnedbEntity, Plugin_Entity_Select<OnedbEntity>> select = H
				.plugins(session).select();
		return plugin(select).ifHas(propertyType);
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
