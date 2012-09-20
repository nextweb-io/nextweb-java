package io.nextweb.plugins;

import io.nextweb.Entity;
import io.nextweb.operations.EntitySelectOperations;

public interface Entity_SelectPlugin<EntityType extends Entity> extends
		EntityPlugin<EntityType>, EntitySelectOperations {

}
