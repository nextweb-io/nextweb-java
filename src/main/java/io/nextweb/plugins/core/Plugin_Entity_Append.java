package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.EntityAppendOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_Append<EntityType extends Entity> extends
		EntityPlugin<EntityType>, EntityAppendOperations {

}
