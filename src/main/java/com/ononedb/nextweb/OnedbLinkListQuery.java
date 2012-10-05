package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Session;
import io.nextweb.fn.BooleanResult;
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
	public void get(final Callback<LinkList> callback) {
		result.get(callback);
	}

	@Override
	public void get(final Closure<LinkList> callback) {
		result.get(callback);
	}

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public ListQuery select(final Link propertyType) {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public ListQuery selectAll(final Link propertyType) {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public LinkListQuery selectAllLinks() {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public ListQuery selectAll() {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public ListQuery setValue(final Object newValue) {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public ListQuery setValueSafe(final Object newValue) {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public BooleanResult has(final Link propertyType) {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public LinkListQuery catchExceptions(final ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	public OnedbLinkListQuery(final OnedbSession session,
			final Result<LinkList> linkListQuery,
			final ExceptionManager parentExceptionManager) {
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
	public LinkListQuery catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public LinkListQuery catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

}
