package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntity;

public class P_Entity_Append_Factory implements
		PluginFactory<OnedbEntity, P_Entity_Append> {

	public static final P_Entity_Append_Factory FACTORY = new P_Entity_Append_Factory();

	@Override
	public P_Entity_Append create(OnedbEntity forObject) {
		P_Entity_Append plugin = new P_Entity_Append();
		plugin.injectObject(forObject);
		return plugin;
	}

}
