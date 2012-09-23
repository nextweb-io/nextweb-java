package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntity;

public class P_Entity_Select_Factory implements
		PluginFactory<OnedbEntity, P_Entity_Select> {

	public static final P_Entity_Select_Factory FACTORY = new P_Entity_Select_Factory();

	@Override
	public P_Entity_Select create(OnedbEntity forObject) {
		P_Entity_Select plugin = new P_Entity_Select();
		plugin.injectObject(forObject);
		return plugin;
	}

}
