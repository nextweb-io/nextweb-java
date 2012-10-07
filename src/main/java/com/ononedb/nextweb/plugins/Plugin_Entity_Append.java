package com.ononedb.nextweb.plugins;

import io.nextweb.Entity;
import io.nextweb.operations.entity.EntityAppendOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_Append<EntityType extends Entity> extends
		EntityAppendOperations, EntityPlugin<EntityType> {

}
