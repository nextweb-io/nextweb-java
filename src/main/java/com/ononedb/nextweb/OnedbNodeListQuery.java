package com.ononedb.nextweb;

import io.nextweb.EntityList;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.ListQuery;
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
	public void get(Callback<NodeList> callback) {
		result.get(callback);
	}

	@Override
	public ListQuery catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public OnedbSession getOnedbSession() {
		return session;
	}

	public OnedbNodeListQuery(OnedbSession session,
			ExceptionManager parentExceptionManager, Result<NodeList> result) {
		super();
		this.result = result;
		this.session = session;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <GType extends EntityList, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {
		return Plugins.plugin((GType) this, factory);
	}

	@Override
	public ListQuery select(final Link propertyType) {
		return plugin(H.plugins(getSession()).selectForLists()).select(
				propertyType);
	}

	@Override
	public ListQuery selectAll(Link propertyType) {
		return plugin(H.plugins(getSession()).selectForLists()).selectAll(
				propertyType);
	}

	@Override
	public LinkListQuery selectAllLinks() {
		return plugin(H.plugins(getSession()).selectForLists())
				.selectAllLinks();
	}

	@Override
	public ListQuery selectAll() {
		return plugin(H.plugins(getSession()).selectForLists()).selectAll();
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
					public void apply(NodeList result) {
						result.each(f);
					}

				}));

		return this;
	}

	@Override
	public ListQuery catchUnauthorized(UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public ListQuery catchUndefined(UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public void get(Closure<NodeList> callback) {
		result.get(callback);

	}

}
