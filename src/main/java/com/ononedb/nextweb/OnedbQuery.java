package com.ononedb.nextweb;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
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
			ExceptionManager fallbackExceptionManager, Result<Node> result) {
		super();
		this.result = result;
		this.session = session;

		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		this.exceptionManager.catchAuthorizationExceptions(listener);
	}

	@Override
	public void catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
	}

	@Override
	public void catchUndefinedExceptions(UndefinedExceptionListener listener) {
		this.exceptionManager.catchUndefinedExceptions(listener);

	}

	@Override
	public Node get() {
		return result.get();
	}

	@Override
	public void get(ResultCallback<Node> callback) {
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

}