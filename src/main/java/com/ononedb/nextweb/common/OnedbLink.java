package com.ononedb.nextweb.common;

import io.nextweb.Link;
import io.nextweb.Query;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;

import com.ononedb.nextweb.OnedbSession;

public class OnedbLink implements Link, OnedbObject {

	private final OnedbSession session;
	private final String uri;

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {

		return Plugins.plugin(this, factory);
	}

	@Override
	public Query select(Link propertyType) {

		return null;
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public String getUri() {

		return uri;
	}

	@Override
	public String uri() {
		return getUri();
	}

	public OnedbLink(OnedbSession session, String uri) {
		super();
		this.session = session;
		this.uri = uri;
	}

}
