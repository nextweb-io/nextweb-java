package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntity;

public class EntityPlugin_Select_Factory implements
		PluginFactory<OnedbEntity, EntityPlugin_Select> {

	public static final EntityPlugin_Select_Factory FACTORY = new EntityPlugin_Select_Factory();

	@Override
	public EntityPlugin_Select create(OnedbEntity forObject) {
		EntityPlugin_Select plugin = new EntityPlugin_Select();
		plugin.injectObject(forObject);
		return plugin;
	}

}
