package com.ononedb.nextweb;

import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.core.domain.OneClient;

public class OnedbSession implements Session {

	private final OneClient client;

	@Override
	public Query load(String uri) {

		return null;

	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	public OnedbSession(OneClient client) {
		super();
		this.client = client;
	}

}
