package com.ononedb.nextweb;

import io.nextweb.EntityList;
import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.ListQuery;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import one.async.joiner.ListCallback;
import one.async.joiner.ListCallbackJoiner;
import one.async.joiner.LocalCallback;

import com.ononedb.nextweb.common.H;

public class OnedbLinkList implements LinkList, OnedbEntityList {

	private final List<Link> list;
	private final Result<NodeList> result;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	@Override
	public int size() {

		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<Link> iterator() {

		return list.iterator();
	}

	@Override
	public Link get(int index) {
		return list.get(index);
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
	public OnedbSession getOnedbSession() {

		return this.session;
	}

	public OnedbLinkList(OnedbSession session,
			ExceptionManager parentExceptionManager, final List<Link> list) {
		super();
		this.session = session;
		this.list = list;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
		this.result = session.getEngine().createResult(exceptionManager,
				session, new AsyncResult<NodeList>() {

					@Override
					public void get(final Callback<NodeList> callback) {

						ListCallbackJoiner<Link, Node> joiner = new ListCallbackJoiner<Link, Node>(
								list, new ListCallback<Node>() {

									@Override
									public void onSuccess(List<Node> responses) {
										callback.onSuccess(H.factory(
												OnedbLinkList.this)
												.createNodeList(
														getOnedbSession(),
														getExceptionManager(),
														responses));
									}

									@Override
									public void onFailure(Throwable t) {
										callback.onFailure(Fn
												.exception(this, t));
									}

								});

						for (Link link : list) {

							final LocalCallback<Node> localCallback = joiner
									.createCallback(link);

							// TODO how to deal with 'unresolvable' nodes
							// maybe not forward exceptions for every call?
							link.get(CallbackFactory.embeddedCallback(
									getExceptionManager(), callback,
									new Closure<Node>() {

										@Override
										public void apply(Node o) {
											localCallback.onSuccess(o);
										}
									}));

						}

					}
				});
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
	public NodeList get() {

		return result.get();
	}

	@Override
	public void get(Callback<NodeList> callback) {
		result.get(callback);
	}

	@Override
	public void get(Closure<NodeList> callback) {
		result.get(callback);
	}

	@Override
	public LinkList catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public LinkList each(Closure<Node> f) {
		H.each(this, list, f);
		return this;
	}

	@Override
	public LinkList catchUndefined(UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public LinkList catchUnauthorized(UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public List<Link> asList() {
		return Collections.unmodifiableList(list);
	}

}
