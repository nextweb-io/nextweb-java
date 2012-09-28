package com.ononedb.nextweb;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.fn.BooleanResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.IntegerResult;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import io.nextweb.plugins.core.Plugin_Entity_Append;
import io.nextweb.plugins.core.Plugin_Entity_ClearVersions;
import io.nextweb.plugins.core.Plugin_Entity_Remove;
import io.nextweb.plugins.core.Plugin_Entity_Select;
import io.nextweb.plugins.core.Plugin_Entity_SetValue;
import one.core.nodes.OneTypedReference;
import one.core.nodes.OneValue;

import com.ononedb.nextweb.common.H;

public class OnedbNode implements Node, OnedbEntity {

	private final OnedbSession session;
	private final OneTypedReference<?> node;
	private final ExceptionManager exceptionManager;

	@Override
	public String getUri() {

		return node.getId();
	}

	@Override
	public String uri() {

		return getUri();
	}

	@Override
	public Object value() {

		return getValue();
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
	public OnedbSession getOnedbSession() {
		return session;
	}

	public OnedbNode(final OnedbSession session,
			final ExceptionManager parentExceptionManager,
			final OneTypedReference<?> node) {
		super();
		this.session = session;
		this.node = node;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
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

}
