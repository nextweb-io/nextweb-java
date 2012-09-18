package com.ononedb.nextweb;

import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.core.domain.OneClient;

import com.ononedb.nextweb.common.OnedbFactory;

public class OnedbSession implements Session {

	private final OnedbNextwebEngine engine;
	private final OneClient client;

	@Override
	public Query load(String uri) {

		return null;

	}

	public OneClient getClient() {
		return client;
	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
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

	public OnedbSession(OnedbNextwebEngine engine, OneClient client) {
		super();
		this.engine = engine;
		this.client = client;
	}

}
