package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.common.LoginResult;
import io.nextweb.common.Postbox;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.BasicResult;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.fn.SuccessFail;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.core.domain.OneClient;

import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.internal.OnedbFactory;
import com.ononedb.nextweb.plugins.Plugin_Session_Core;

public class OnedbSession implements Session {

	private final OnedbNextwebEngine engine;
	private final OneClient client;
	private final ExceptionManager exceptionManager;

	public OneClient getClient() {
		return client;
	}

	public OnedbFactory getFactory() {
		return getOnedbEngine().getFactory();
	}

	public OnedbNextwebEngine getOnedbEngine() {
		return this.engine;
	}

	@Override
	public NextwebEngine getEngine() {
		return getOnedbEngine();
	}

	public OnedbSession(final OnedbNextwebEngine engine, final OneClient client) {
		super();
		this.engine = engine;
		this.client = client;
		this.exceptionManager = engine.getFactory()
				.createExceptionManager(null);
	}

	@Override
	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	private final Plugin_Session_Core<Session> core() {
		final PluginFactory<Session, Plugin_Session_Core<Session>> pluginFactory = H
				.onedbDefaultPluginFactory().session();
		final Plugin_Session_Core<Session> core = pluginFactory.create(this);
		return core;
	}

	/*
	 * Session operations
	 */

	@Override
	public Result<Success> close() {
		return core().close();
	}

	@Override
	public Result<Success> commit() {
		return core().commit();
	}

	@Override
	public Link node(final String uri) {
		return core().node(uri);
	}

	@Override
	public Link node(final String uri, final String secret) {
		return core().node(uri, secret);
	}

	@Override
	public Link node(final Link link) {
		return core().node(link);
	}

	@Override
	public Link node(final Node node) {
		return core().node(node);
	}

	@Override
	public Session getAll(final BasicResult<?>... results) {
		return core().getAll(results);
	}

	@Override
	public Result<SuccessFail> getAll(final boolean asynchronous,
			final BasicResult<?>... results) {
		return core().getAll(asynchronous, results);
	}

	@Override
	public Query seed() {
		return core().seed();
	}

	@Override
	public Query seed(final String seedType) {
		return core().seed(seedType);
	}

	@Override
	public Query createRealm(final String realmTitle, final String realmType,
			final String apiKey) {
		return core().createRealm(realmTitle, realmType, apiKey);
	}

	@Override
	public Result<Postbox> createPostbox(final String realmTitle,
			final String postboxType, final String apiKey) {
		return core().createPostbox(realmTitle, postboxType, apiKey);
	}

	@Override
	public Result<Success> post(final Object value, final String toUri,
			final String secret) {
		return core().post(value, toUri, secret);
	}

	@Override
	public LoginResult login(final String email, final String password) {
		return core().login(email, password);
	}

	@Override
	public LoginResult login(final String email, final String password,
			final Link application) {
		return core().login(email, password, application);
	}

	@Override
	public LoginResult login(final String sessionId) {
		return core().login(sessionId);
	}

	@Override
	public LoginResult login(final String sessionId, final Link application) {
		return core().login(sessionId, application);
	}

	@Override
	public LoginResult register(final String email, final String password,
			final Link application) {
		return core().register(email, password, application);
	}

	@Override
	public LoginResult register(final String email, final String password) {
		return core().register(email, password);
	}

}
