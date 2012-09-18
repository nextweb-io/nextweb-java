package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.core.nodes.OneTypedReference;
import one.core.nodes.OneValue;

import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.plugins.EntityPlugin_Select;

public class OnedbNode implements Node, OnedbEntity {

	private final OnedbSession session;
	private final OneTypedReference<?> node;
	private final ExceptionManager exceptionManager;

	@Override
	public Query select(Link propertyType) {
		return new EntityPlugin_Select(this).select(propertyType);
	}

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
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {

		return Plugins.plugin(this, factory);
	}

	@Override
	public OnedbSession getOnedbSession() {
		return session;
	}

	public OnedbNode(OnedbSession session,
			ExceptionManager fallbackExceptionManager, OneTypedReference<?> node) {
		super();
		this.session = session;
		this.node = node;
		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
	}

	@Override
	public Node get() {
		return this;
	}

	@Override
	public void get(ResultCallback<Node> callback) {
		callback.onSuccess(this);
	}

	@Override
	public void catchExceptions(ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return this.exceptionManager;
	}

}
