package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.entity.EntityClearVersionsOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_ClearVersions<EntityType extends Entity> extends
		EntityClearVersionsOperations, EntityPlugin<EntityType> {

}
