package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.EntityAppendOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Entity_AppendPlugin<EntityType extends Entity> extends
		EntityPlugin<EntityType>, EntityAppendOperations {

}
