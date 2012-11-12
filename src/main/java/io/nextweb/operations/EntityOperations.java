package io.nextweb.operations;

import io.nextweb.operations.entity.EntityAppendOperations;
import io.nextweb.operations.entity.EntityClearVersionsOperations;
import io.nextweb.operations.entity.EntityReloadOperations;
import io.nextweb.operations.entity.EntityRemoveOperations;
import io.nextweb.operations.entity.EntitySelectOperations;
import io.nextweb.operations.entity.EntitySetValueOperations;

public interface EntityOperations extends EntitySelectOperations,
		EntityRemoveOperations, EntityClearVersionsOperations,
		EntityAppendOperations, EntitySetValueOperations,
		EntityMonitorOperations, EntityReloadOperations {

}
