package com.ononedb.nextweb;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.Session;
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
		Object dereferenced = H.dsl(this).dereference(node)
				.in(session.getClient());

		if (dereferenced instanceof OneValue<?>) {
			return ((OneValue<?>) dereferenced).getValue();
		}

		return dereferenced;
	}

	@Override
	public Node setValue(final Object newValue) {
		return (Node) plugin(H.plugins(getSession()).setValue()).setValue(
				newValue);
	}

	@Override
	public Result<Success> setValueSafe(Object newValue) {
		return plugin(H.plugins(getSession()).setValue())
				.setValueSafe(newValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ValueType> ValueType value(Class<ValueType> type) {
		Object value = getValue();

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

	public OnedbNode(OnedbSession session,
			ExceptionManager parentExceptionManager, OneTypedReference<?> node) {
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
	public void get(Callback<Node> callback) {
		callback.onSuccess(this);
	}

	@Override
	public Node catchExceptions(ExceptionListener listener) {
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
	public Result<Integer> clearVersions(int keepVersions) {
		return plugin(H.plugins(session).clearVersions()).clearVersions(
				keepVersions);
	}

	@Override
	public String toString() {
		return "node(\"" + this.getUri() + "\", " + this.getValue().getClass()
				+ ")";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <GType extends Entity, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {
		return Plugins.plugin((GType) this, factory);
	}

	@Override
	public Node catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		exceptionManager.catchAuthorizationExceptions(listener);
		return this;
	}

	@Override
	public Node catchUndefinedExceptions(UndefinedExceptionListener listener) {
		exceptionManager.catchUndefinedExceptions(listener);
		return this;
	}

	@Override
	public void get(Closure<Node> callback) {
		callback.apply(this);
	}

}
