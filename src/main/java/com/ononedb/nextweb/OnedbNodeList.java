package com.ononedb.nextweb;

import io.nextweb.EntityList;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import java.util.Iterator;
import java.util.List;

import one.async.joiner.ListCallback;
import one.async.joiner.ListCallbackJoiner;
import one.async.joiner.LocalCallback;

import com.ononedb.nextweb.common.H;

public class OnedbNodeList implements OnedbEntityList<NodeList>, NodeList {

	private final List<Node> list;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	@SuppressWarnings("unchecked")
	@Override
	public <GType extends EntityList<NodeList>, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {

		return Plugins.plugin((GType) this, factory);
	}

	@Override
	public NodeListQuery select(final Link propertyType) {

		AsyncResult<NodeList> selectResult = new AsyncResult<NodeList>() {

			@Override
			public void get(final Callback<NodeList> callback) {

				ListCallbackJoiner<Node, Node> joiner = new ListCallbackJoiner<Node, Node>(
						list, new ListCallback<Node>() {

							@Override
							public void onFailure(Throwable arg0) {
								callback.onFailure(this, arg0);
							}

							@Override
							public void onSuccess(List<Node> nodes) {

								callback.onSuccess(H
										.factory(OnedbNodeList.this)
										.createNodeList(getOnedbSession(),
												getExceptionManager(), nodes));
							}
						});

				for (Node child : list) {

					final LocalCallback<Node> localCallback = joiner
							.createCallback(child);

					child.select(propertyType)
							.catchExceptions(new ExceptionListener() {

								@Override
								public void onFailure(Object origin, Throwable t) {

									localCallback.onFailure(t);
								}
							})
							.get(CallbackFactory.eagerCallback(
									OnedbNodeList.this.getSession(),
									OnedbNodeList.this.getExceptionManager(),
									new Closure<Node>() {

										@Override
										public void apply(Node o) {
											localCallback.onSuccess(o);
										}

									}).catchFailures(new ExceptionListener() {

								@Override
								public void onFailure(Object origin, Throwable t) {
									localCallback.onFailure(t);
								}
							}));

				}

			}
		};

		return H.factory(this).createNodeListQuery(getOnedbSession(),
				exceptionManager, selectResult);

	}

	@Override
	public NodeListQuery selectAll(Link propertyType) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public LinkListQuery selectAllLinks() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public NodeListQuery selectAll() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public OnedbSession getOnedbSession() {

		return this.session;
	}

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
	public Iterator<Node> iterator() {

		return list.iterator();
	}

	@Override
	public Node get(int index) {
		return list.get(index);
	}

	@Override
	public String toString() {
		return list.toString();
	}

	public OnedbNodeList(OnedbSession session,
			ExceptionManager parentExceptionManager, List<Node> list) {
		super();
		this.list = list;
		this.session = session;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
	}

	@Override
	public NodeList each(Closure<Node> f) {
		H.each(this, list, f);
		return this;
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

		return this;
	}

	@Override
	public void get(Callback<NodeList> callback) {
		callback.onSuccess(this);
	}

	@Override
	public NodeList catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public NodeList catchUnauthorized(
			UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public NodeList catchUndefined(UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public void get(Closure<NodeList> callback) {
		callback.apply(this);
	}

}
