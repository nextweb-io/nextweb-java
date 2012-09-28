package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import io.nextweb.plugins.core.Plugin_EntityList_Select;

import com.ononedb.nextweb.common.H;

public class OnedbNodeListQuery implements ListQuery, OnedbEntityList {

	private final Result<NodeList> result;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	@Override
	public NodeList get() {
		return result.get();
	}

	@Override
	public void get(final Callback<NodeList> callback) {
		result.get(callback);
	}

	@Override
	public ListQuery catchExceptions(final ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public OnedbSession getOnedbSession() {
		return session;
	}

	public OnedbNodeListQuery(final OnedbSession session,
			final ExceptionManager parentExceptionManager,
			final Result<NodeList> result) {
		super();
		this.result = result;
		this.session = session;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
	}

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
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
	public ListQuery each(final Closure<Node> f) {
		this.result.get(CallbackFactory.lazyCallback(this,
				new Closure<NodeList>() {

					@Override
					public void apply(final NodeList result) {
						result.each(f);
					}

				}));

		return this;
	}

	@Override
	public ListQuery catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public ListQuery catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public void get(final Closure<NodeList> callback) {
		result.get(callback);

	}

	/**
	 * Plugins
	 */

	@Override
	public ListQuery select(final Link propertyType) {
		final PluginFactory<OnedbEntityList, Plugin_EntityList_Select<OnedbEntityList>> selectForLists = H
				.plugins(getSession()).selectForLists();
		return plugin(selectForLists).select(propertyType);
	}

	@Override
	public ListQuery selectAll(final Link propertyType) {
		final PluginFactory<OnedbEntityList, Plugin_EntityList_Select<OnedbEntityList>> selectForLists = H
				.plugins(getSession()).selectForLists();
		return plugin(selectForLists).selectAll(propertyType);
	}

	@Override
	public LinkListQuery selectAllLinks() {
		final PluginFactory<OnedbEntityList, Plugin_EntityList_Select<OnedbEntityList>> selectForLists = H
				.plugins(getSession()).selectForLists();
		return plugin(selectForLists).selectAllLinks();
	}

	@Override
	public ListQuery selectAll() {
		final PluginFactory<OnedbEntityList, Plugin_EntityList_Select<OnedbEntityList>> selectForLists = H
				.plugins(getSession()).selectForLists();
		return plugin(selectForLists).selectAll();
	}

}
