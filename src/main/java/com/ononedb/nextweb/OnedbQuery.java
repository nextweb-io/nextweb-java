package com.ononedb.nextweb;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.fn.BooleanResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.IntegerResult;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import com.ononedb.nextweb.common.H;

public class OnedbQuery implements Query, OnedbEntity {

	private final Result<Node> result;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	public OnedbQuery(OnedbSession session,
			ExceptionManager parentExceptionManager, Result<Node> result) {
		super();
		this.result = result;
		this.session = session;

		this.exceptionManager = session.getFactory().createExceptionManager(
				parentExceptionManager);
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public Query catchUnauthorized(UnauthorizedListener listener) {
		this.exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public Query catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public Query catchUndefined(UndefinedListener listener) {
		this.exceptionManager.catchUndefined(listener);
		return this;

	}

	@Override
	public Node get() {
		return result.get();
	}

	@Override
	public void get(Callback<Node> callback) {
		result.get(callback);
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
	public Query select(Link propertyType) {
		return plugin(H.plugins(session).select()).select(propertyType);
	}

	@Override
	public ListQuery selectAll(Link propertyType) {
		return plugin(H.plugins(session).select()).selectAll(propertyType);
	}

	@Override
	public LinkListQuery selectAllLinks() {
		return plugin(H.plugins(session).select()).selectAllLinks();
	}

	@Override
	public ListQuery selectAll() {
		return plugin(H.plugins(session).select()).selectAll();
	}

	@Override
	public BooleanResult has(Link propertyType) {
		return plugin(H.plugins(session).select()).has(propertyType);
	}

	@Override
	public Result<Success> remove(Entity entity) {
		return plugin(H.plugins(session).remove()).remove(entity);
	}

	@Override
	public Query append(Object value) {

		return plugin(H.plugins(session).append()).append(value);
	}

	@Override
	public Query append(Object value, String atAddress) {
		return plugin(H.plugins(session).append()).append(value, atAddress);
	}

	@Override
	public Query appendValue(Object value) {
		return plugin(H.plugins(session).append()).appendValue(value);
	}

	@Override
	public Query setValue(Object newValue) {
		return plugin(H.plugins(session).setValue()).setValue(newValue);
	}

	@Override
	public Query setValueSafe(Object newValue) {
		return plugin(H.plugins(session).setValue()).setValueSafe(newValue);
	}

	@Override
	public Query append(Entity entity) {

		return plugin(H.plugins(session).append()).append(entity);
	}

	@Override
	public Query appendSafe(Object value) {
		Query appendSafe = plugin(H.plugins(session).append())
				.appendSafe(value);

		return appendSafe;
	}

	@Override
	public Query appendSafe(Object value, String atAddress) {
		return plugin(H.plugins(session).append()).appendSafe(value, atAddress);
	}

	@Override
	public Query appendValueSafe(Object value) {
		return plugin(H.plugins(session).append()).appendValueSafe(value);
	}

	@Override
	public Query appendSafe(Entity entity) {
		return plugin(H.plugins(session).append()).appendSafe(entity);
	}

	@Override
	public Query insert(Object value, int atIndex) {
		return plugin(H.plugins(session).append()).insert(value, atIndex);
	}

	@Override
	public Query insert(Object value, String atAddress, int atIndex) {
		return plugin(H.plugins(session).append()).insert(value, atAddress,
				atIndex);
	}

	@Override
	public Query insertValue(Object value, int atIndex) {
		return plugin(H.plugins(session).append()).insertValue(value, atIndex);
	}

	@Override
	public Query insert(Entity entity, int atIndex) {
		return plugin(H.plugins(session).append()).insertValue(entity, atIndex);
	}

	@Override
	public Query insertSafe(Object value, int atIndex) {
		return plugin(H.plugins(session).append()).insertSafe(value, atIndex);
	}

	@Override
	public Query insertSafe(Object value, String atAddress, int atIndex) {
		return plugin(H.plugins(session).append()).insertSafe(value, atAddress,
				atIndex);
	}

	@Override
	public Query insertValueSafe(Object value, int atIndex) {
		return plugin(H.plugins(session).append()).insertValueSafe(value,
				atIndex);
	}

	@Override
	public Query insertSafe(Entity entity, int atIndex) {
		return plugin(H.plugins(session).append()).insertSafe(entity, atIndex);
	}

	@Override
	public IntegerResult clearVersions(int keepVersions) {
		return plugin(H.plugins(session).clearVersions()).clearVersions(
				keepVersions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <GType extends Entity, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory) {

		return Plugins.plugin((GType) this, factory);
	}

	@Override
	public void get(Closure<Node> callback) {
		result.get(callback);
	}

}
