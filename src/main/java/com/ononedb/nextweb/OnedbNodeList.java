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
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import io.nextweb.plugins.core.Plugin_EntityList_Select;
import io.nextweb.plugins.core.Plugin_EntityList_SetValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ononedb.nextweb.common.H;

public class OnedbNodeList implements OnedbEntityList, NodeList {

	private final List<Node> list;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
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
	public boolean contains(final Object o) {

		return list.contains(o);
	}

	@Override
	public Iterator<Node> iterator() {

		return list.iterator();
	}

	@Override
	public Node get(final int index) {
		return list.get(index);
	}

	@Override
	public String toString() {
		return list.toString();
	}

	public OnedbNodeList(final OnedbSession session,
			final ExceptionManager parentExceptionManager, final List<Node> list) {
		super();
		this.list = list;
		this.session = session;
		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
	}

	@Override
	public NodeList each(final Closure<Node> f) {
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
	public List<Object> values() {
		final List<Object> values = new ArrayList<Object>(this.list.size());

		for (final Node n : this.list) {
			values.add(n.value());
		}
		return values;
	}

	@Override
	public void get(final Callback<NodeList> callback) {
		callback.onSuccess(this);
	}

	@Override
	public NodeList catchExceptions(final ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public NodeList catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public NodeList catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public void get(final Closure<NodeList> callback) {
		callback.apply(this);
	}

	@Override
	public List<Node> asList() {
		return Collections.unmodifiableList(list);
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
