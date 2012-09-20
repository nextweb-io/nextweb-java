package io.nextweb.plugins;

import io.nextweb.Entity;

public interface DefaultPluginFactory {

	public <GEntity extends Entity, GPlugin extends Entity_SelectPlugin<GEntity>> PluginFactory<GEntity, GPlugin> select();

}
