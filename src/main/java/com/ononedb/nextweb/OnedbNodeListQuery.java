package com.ononedb.nextweb;

import io.nextweb.EntityList;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Session;
import io.nextweb.fn.BooleanResult;
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
import io.nextweb.plugins.core.Plugin_EntityList_SetValue;

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

	private final Plugin_EntityList_SetValue<EntityList> setValuePlugin() {
		final PluginFactory<EntityList, Plugin_EntityList_SetValue<EntityList>> setValueForLists = H
				.plugins(session).setValueForLists();
		return plugin(setValueForLists);
	}

	private final Plugin_EntityList_Select<EntityList> selectPlugin() {
		final PluginFactory<EntityList, Plugin_EntityList_Select<EntityList>> selectForLists = H
				.plugins(session).selectForLists();
		return plugin(selectForLists);
	}

	@Override
	public ListQuery select(final Link propertyType) {
		return selectPlugin().select(propertyType);
	}

	@Override
	public ListQuery selectAll(final Link propertyType) {

		return selectPlugin().selectAll(propertyType);
	}

	@Override
	public LinkListQuery selectAllLinks() {
		return selectPlugin().selectAllLinks();
	}

	@Override
	public ListQuery selectAll() {
		return selectPlugin().selectAll();
	}

	@Override
	public BooleanResult has(final Link propertyType) {
		return selectPlugin().has(propertyType);
	}

	@Override
	public ListQuery setValue(final Object newValue) {
		return setValuePlugin().setValue(newValue);
	}

	@Override
	public ListQuery setValueSafe(final Object newValue) {
		return setValuePlugin().setValueSafe(newValue);
	}

}
