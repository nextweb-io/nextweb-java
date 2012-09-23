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
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import java.util.Iterator;
import java.util.List;

import com.ononedb.nextweb.common.H;

public class OnedbLinkList implements LinkList, OnedbEntityList<LinkList> {

	private final List<Link> list;
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
	public <GType extends EntityList<LinkList>, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {

		return Plugins.plugin((GType) this, factory);
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
	public OnedbSession getOnedbSession() {

		return this.session;
	}

	public OnedbLinkList(OnedbSession session,
			ExceptionManager parentExceptionManager, List<Link> list) {
		super();
		this.session = session;
		this.list = list;
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
	public LinkList get() {

		return this;
	}

	@Override
	public void get(Callback<LinkList> callback) {
		callback.onSuccess(this);
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
	public LinkList catchUndefinedExceptions(UndefinedExceptionListener listener) {
		exceptionManager.catchUndefinedExceptions(listener);
		return this;
	}

	@Override
	public LinkList catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		exceptionManager.catchAuthorizationExceptions(listener);
		return this;
	}

	@Override
	public void get(Closure<LinkList> callback) {
		callback.apply(this);
	}

}
