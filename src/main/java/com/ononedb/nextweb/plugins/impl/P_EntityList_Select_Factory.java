package com.ononedb.nextweb.plugins.impl;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntityList;

public class P_EntityList_Select_Factory implements
		PluginFactory<OnedbEntityList, P_EntityList_Select> {

	public static final P_EntityList_Select_Factory FACTORY = new P_EntityList_Select_Factory();

	@Override
	public P_EntityList_Select create(OnedbEntityList forObject) {
		P_EntityList_Select plugin = new P_EntityList_Select();
		plugin.injectObject(forObject);
		return plugin;
	}

}
