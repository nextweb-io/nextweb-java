package com.ononedb.nextweb.plugins;

import io.nextweb.Entity;
import io.nextweb.operations.entity.EntitySetValueOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_SetValue<EntityType extends Entity> extends
		EntitySetValueOperations, EntityPlugin<EntityType> {

}
