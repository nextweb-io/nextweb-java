package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntity;

public class P_Entity_ClearVersions_Factory implements
		PluginFactory<OnedbEntity, P_Entity_ClearVersions> {

	public static final P_Entity_ClearVersions_Factory FACTORY = new P_Entity_ClearVersions_Factory();

	@Override
	public P_Entity_ClearVersions create(OnedbEntity obj) {
		P_Entity_ClearVersions plugin = new P_Entity_ClearVersions();
		plugin.injectObject(obj);
		return plugin;
	}

}
