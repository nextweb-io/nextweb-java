package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Query;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

public class OnedbQuery implements Query {

	@Override
	public Query select(Link propertyType) {

		return null;
	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

}
