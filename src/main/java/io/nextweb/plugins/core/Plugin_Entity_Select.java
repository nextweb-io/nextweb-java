package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.entity.EntitySelectOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_Select<EntityType extends Entity> extends
		EntityPlugin<EntityType>, EntitySelectOperations {

}
