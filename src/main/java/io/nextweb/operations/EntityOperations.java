package io.nextweb.operations;

import io.nextweb.operations.entity.EntityClearVersionsOperations;
import io.nextweb.operations.entity.EntityRemoveOperations;
import io.nextweb.operations.entity.EntitySelectOperations;

public interface EntityOperations extends EntitySelectOperations,
		EntityRemoveOperations, EntityClearVersionsOperations,
		EntityAppendOperations, EntitySetValueOperations {

}
