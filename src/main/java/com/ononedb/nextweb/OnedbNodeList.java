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
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import java.util.Iterator;
import java.util.List;

import com.ononedb.nextweb.common.H;

public class OnedbNodeList implements OnedbEntityList<NodeList>, NodeList {

	private final List<Node> list;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	@Override
	public <PluginType extends Plugin<EntityList<NodeList>>> PluginType plugin(
			PluginFactory<EntityList<NodeList>, PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public NodeListQuery select(Link propertyType) {
		throw new RuntimeException("Not yet implemented");
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
			ExceptionManager fallbackExceptionManager, List<Node> list) {
		super();
		this.list = list;
		this.session = session;
		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
	}

	@Override
	public NodeList each(Closure<Node> f) {
		H.each(list, f);
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
	public void get(ResultCallback<NodeList> callback) {
		callback.onSuccess(this);
	}

	@Override
	public void catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
	}

}
