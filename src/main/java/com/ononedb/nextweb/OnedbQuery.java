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
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import com.ononedb.nextweb.common.H;

public class OnedbQuery implements Query, OnedbEntity {

	private final Result<Node> result;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	public OnedbQuery(OnedbSession session,
			ExceptionManager parentExceptionManager, Result<Node> result) {
		super();
		this.result = result;
		this.session = session;

		this.exceptionManager = session.getFactory().createExceptionManager();
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public Query catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		this.exceptionManager.catchAuthorizationExceptions(listener);
		return this;
	}

	@Override
	public Query catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public Query catchUndefinedExceptions(UndefinedExceptionListener listener) {
		this.exceptionManager.catchUndefinedExceptions(listener);
		return this;

	}

	@Override
	public Node get() {
		return result.get();
	}

	@Override
	public void get(Callback<Node> callback) {
		result.get(callback);
	}

	@Override
	public Session getSession() {

		return this.session;
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
	public <PluginType extends Plugin<Entity>> PluginType plugin(
			PluginFactory<Entity, PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public void get(Closure<Node> callback) {
		result.get(callback);
	}

}
