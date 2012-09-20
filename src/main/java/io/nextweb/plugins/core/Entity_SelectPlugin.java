package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.EntitySelectOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Entity_SelectPlugin<EntityType extends Entity> extends
		EntityPlugin<EntityType>, EntitySelectOperations {

}
