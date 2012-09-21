package com.ononedb.nextweb;

import io.nextweb.EntityList;
import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeListQuery;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

public class OnedbLinkListQuery implements LinkListQuery,
		OnedbEntityList<LinkList> {

	private final OnedbSession session;
	private final Result<LinkList> result;
	private final ExceptionManager exceptionManager;

	@Override
	public LinkList get() {
		return result.get();
	}

	@Override
	public void get(ResultCallback<LinkList> callback) {
		result.get(callback);
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
	public void catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public <PluginType extends Plugin<EntityList<LinkList>>> PluginType plugin(
			PluginFactory<EntityList<LinkList>, PluginType> factory) {

		return Plugins.plugin(this, factory);
	}

	public OnedbLinkListQuery(OnedbSession session,
			Result<LinkList> linkListQuery,
			ExceptionManager fallbackExceptionManager) {
		super();
		this.session = session;
		this.result = linkListQuery;
		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
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
	public LinkListQuery each(final Closure<Node> f) {

		this.result.get(new ResultCallback<LinkList>() {

			@Override
			public void onSuccess(LinkList result) {
				result.each(f);
			}
		});

		return this;
	}

}
