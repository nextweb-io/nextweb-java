package com.ononedb.nextweb;

import io.nextweb.EntityList;
import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import java.util.Iterator;
import java.util.List;

public class OnedbLinkList implements LinkList, OnedbEntityList {

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

	@Override
	public <PluginType extends Plugin<EntityList>> PluginType plugin(
			PluginFactory<EntityList, PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public Query select(Link propertyType) {
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
			ExceptionManager fallbackExceptionManager, List<Link> list) {
		super();
		this.session = session;
		this.list = list;
		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
	}

}
