package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.EntityRemoveOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_Remove<EntityType extends Entity> extends
		EntityPlugin<EntityType>, EntityRemoveOperations {

}
