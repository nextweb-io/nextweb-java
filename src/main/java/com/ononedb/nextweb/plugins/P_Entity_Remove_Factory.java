package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntity;

public class P_Entity_Remove_Factory implements
		PluginFactory<OnedbEntity, P_Entity_Remove> {

	public final static P_Entity_Remove_Factory FACTORY = new P_Entity_Remove_Factory();

	@Override
	public P_Entity_Remove create(OnedbEntity forObject) {
		P_Entity_Remove plugin = new P_Entity_Remove();
		plugin.injectObject(forObject);
		return plugin;
	}

}
