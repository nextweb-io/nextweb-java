package com.ononedb.nextweb.plugins;

import io.nextweb.Entity;
import io.nextweb.operations.entity.EntityRemoveOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_Remove<EntityType extends Entity> extends
		EntityPlugin<EntityType>, EntityRemoveOperations {

}
