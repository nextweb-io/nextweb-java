package com.ononedb.nextweb.common;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

public class OnedbQuery implements Query {

	private final Result<Node> result;

	@Override
	public Query select(Link propertyType) {

		return new OnedbQuery(;
	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	public OnedbQuery(Result<Node> result) {
		super();
		this.result = result;
	}

}
