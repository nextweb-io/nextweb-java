package com.ononedb.nextweb;

import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

public class OnedbSession implements Session {

	@Override
	public Query load(String uri) {

		return null;

	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

}
