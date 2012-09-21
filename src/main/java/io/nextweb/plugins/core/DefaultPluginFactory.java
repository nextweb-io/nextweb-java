package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.plugins.PluginFactory;

public interface DefaultPluginFactory {

	public <GEntity extends Entity, GPlugin extends Entity_SelectPlugin<GEntity>> PluginFactory<GEntity, GPlugin> select();

	public <GEntity extends EntityList<?>, GPlugin extends EntityList_SelectPlugin<GEntity>> PluginFactory<GEntity, GPlugin> selectForLists();

}
