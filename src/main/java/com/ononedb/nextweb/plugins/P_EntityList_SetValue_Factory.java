package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntityList;

public class P_EntityList_SetValue_Factory implements
		PluginFactory<OnedbEntityList, P_EntityList_SetValue> {

	public static P_EntityList_SetValue_Factory FACTORY = new P_EntityList_SetValue_Factory();

	@Override
	public P_EntityList_SetValue create(final OnedbEntityList forObject) {
		final P_EntityList_SetValue plugin = new P_EntityList_SetValue();
		plugin.injectObject(forObject);
		return plugin;
	}

}
