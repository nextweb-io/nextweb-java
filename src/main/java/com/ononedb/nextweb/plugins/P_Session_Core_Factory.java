package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.core.Plugin_Session_Core;

import com.ononedb.nextweb.OnedbSession;

public class P_Session_Core_Factory implements
		PluginFactory<OnedbSession, Plugin_Session_Core<OnedbSession>> {

	public static final P_Session_Core_Factory FACTORY = new P_Session_Core_Factory();

	@Override
	public Plugin_Session_Core<OnedbSession> create(final OnedbSession forObject) {
		final P_Session_Core plugin = new P_Session_Core();
		plugin.injectObject(forObject);
		return plugin;
	}

}
