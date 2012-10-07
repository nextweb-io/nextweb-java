package com.ononedb.nextweb.plugins.impl;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntity;

public class P_Entity_Monitor_Factory implements
		PluginFactory<OnedbEntity, P_Entity_Monitor> {

	public static P_Entity_Monitor_Factory FACTORY = new P_Entity_Monitor_Factory();

	@Override
	public P_Entity_Monitor create(final OnedbEntity forObject) {
		final P_Entity_Monitor plugin = new P_Entity_Monitor();
		plugin.injectObject(forObject);
		return plugin;
	}

}
