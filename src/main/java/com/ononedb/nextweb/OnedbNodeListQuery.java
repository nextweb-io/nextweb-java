package com.ononedb.nextweb;

import io.nextweb.EntityList;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

public class OnedbNodeListQuery implements NodeListQuery,
		OnedbEntityList<NodeList> {

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
	public NodeListQuery catchExceptions(ExceptionListener listener) {
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
		this.exceptionManager = session.getFactory().createExceptionManager();
	}

	@Override
	public NodeListQuery select(Link propertyType) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public NodeListQuery selectAll(Link propertyType) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public LinkListQuery selectAllLinks() {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public NodeListQuery selectAll() {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public <PluginType extends Plugin<EntityList<NodeList>>> PluginType plugin(
			PluginFactory<EntityList<NodeList>, PluginType> factory) {

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
	public NodeListQuery each(final Closure<Node> f) {
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
	public NodeListQuery catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		exceptionManager.catchAuthorizationExceptions(listener);
		return this;
	}

	@Override
	public NodeListQuery catchUndefinedExceptions(
			UndefinedExceptionListener listener) {
		exceptionManager.catchUndefinedExceptions(listener);
		return this;
	}

	@Override
	public void get(Closure<NodeList> callback) {
		result.get(callback);

	}

}
