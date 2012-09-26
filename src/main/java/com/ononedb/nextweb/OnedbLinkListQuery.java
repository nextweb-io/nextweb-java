package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.NodeListQuery;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

public class OnedbLinkListQuery implements LinkListQuery, OnedbObject {

	private final OnedbSession session;
	private final Result<LinkList> result;
	private final ExceptionManager exceptionManager;

	@Override
	public LinkList get() {
		return result.get();
	}

	@Override
	public void get(Callback<LinkList> callback) {
		result.get(callback);
	}

	@Override
	public void get(Closure<LinkList> callback) {
		result.get(callback);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <GType extends LinkListQuery, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {
		return Plugins.plugin((GType) this, factory);
	}

	@Override
	public NodeListQuery select(final Link propertyType) {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public NodeListQuery selectAll(Link propertyType) {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public LinkListQuery selectAllLinks() {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public NodeListQuery selectAll() {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public LinkListQuery catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	public OnedbLinkListQuery(OnedbSession session,
			Result<LinkList> linkListQuery,
			ExceptionManager parentExceptionManager) {
		super();
		this.session = session;
		this.result = linkListQuery;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
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
	public LinkListQuery catchUnauthorized(UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public LinkListQuery catchUndefined(UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

}
