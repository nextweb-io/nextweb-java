package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbEntityList;

public class EntityListPlugin_Select_Factory implements
		PluginFactory<OnedbEntityList, EntityListPlugin_Select> {

	public static final EntityListPlugin_Select_Factory FACTORY = new EntityListPlugin_Select_Factory();

	@Override
	public EntityListPlugin_Select create(OnedbEntityList forObject) {
		EntityListPlugin_Select plugin = new EntityListPlugin_Select();
		plugin.injectObject(forObject);
		return plugin;
	}

}
