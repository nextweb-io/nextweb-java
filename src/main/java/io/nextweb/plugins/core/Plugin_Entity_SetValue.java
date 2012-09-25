package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.EntitySetValueOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_SetValue<EntityType extends Entity> extends
		EntitySetValueOperations<EntityType>, EntityPlugin<EntityType> {

}
