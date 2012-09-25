package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.plugins.PluginFactory;

public interface DefaultPluginFactory {

	public <GEntity extends Entity, GPlugin extends Plugin_Entity_Select<GEntity>> PluginFactory<GEntity, GPlugin> select();

	public <GEntity extends EntityList<?>, GPlugin extends Plugin_EntityList_Select<GEntity>> PluginFactory<GEntity, GPlugin> selectForLists();

	public <GEntity extends Entity, GPlugin extends Plugin_Entity_Append<GEntity>> PluginFactory<GEntity, GPlugin> append();

	public <GEntity extends Entity, GPlugin extends Plugin_Entity_Remove<GEntity>> PluginFactory<GEntity, GPlugin> remove();

	public <GEntity extends Entity, GPlugin extends Plugin_Entity_ClearVersions<GEntity>> PluginFactory<GEntity, GPlugin> clearVersions();

}
