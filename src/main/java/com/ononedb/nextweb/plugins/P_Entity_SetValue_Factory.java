package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntity;

public class P_Entity_SetValue_Factory implements
		PluginFactory<OnedbEntity, P_Entity_SetValue> {

	public static final P_Entity_SetValue_Factory FACTORY = new P_Entity_SetValue_Factory();

	@Override
	public P_Entity_SetValue create(OnedbEntity forObject) {
		P_Entity_SetValue plugin = new P_Entity_SetValue();
		plugin.injectObject(forObject);
		return plugin;
	}

}
