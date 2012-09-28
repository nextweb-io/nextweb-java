package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
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
import io.nextweb.plugins.core.Plugin_EntityList_Select;

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
	public boolean contains(final Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<Link> iterator() {

		return list.iterator();
	}

	@Override
	public Link get(final int index) {
		return list.get(index);
	}

	@Override
	public OnedbSession getOnedbSession() {

		return this.session;
	}

	public OnedbLinkList(final OnedbSession session,
			final ExceptionManager parentExceptionManager, final List<Link> list) {
		super();
		this.session = session;
		this.list = list;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
		this.result = session.getEngine().createResult(exceptionManager,
				session, new AsyncResult<NodeList>() {

					@Override
					public void get(final Callback<NodeList> callback) {

						final ListCallbackJoiner<Link, Node> joiner = new ListCallbackJoiner<Link, Node>(
								list, new ListCallback<Node>() {

									@Override
									public void onSuccess(
											final List<Node> responses) {
										callback.onSuccess(H.factory(
												OnedbLinkList.this)
												.createNodeList(
														getOnedbSession(),
														getExceptionManager(),
														responses));
									}

									@Override
									public void onFailure(final Throwable t) {
										callback.onFailure(Fn
												.exception(this, t));
									}

								});

						for (final Link link : list) {

							final LocalCallback<Node> localCallback = joiner
									.createCallback(link);

							// TODO how to deal with 'unresolvable' nodes
							// maybe not forward exceptions for every call?
							link.get(CallbackFactory.embeddedCallback(
									getExceptionManager(), callback,
									new Closure<Node>() {

										@Override
										public void apply(final Node o) {
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
	public void get(final Callback<NodeList> callback) {
		result.get(callback);
	}

	@Override
	public void get(final Closure<NodeList> callback) {
		result.get(callback);
	}

	@Override
	public LinkList catchExceptions(final ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public LinkList each(final Closure<Node> f) {
		H.each(this, list, f);
		return this;
	}

	@Override
	public LinkList catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public LinkList catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public List<Link> asList() {
		return Collections.unmodifiableList(list);
	}

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
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
