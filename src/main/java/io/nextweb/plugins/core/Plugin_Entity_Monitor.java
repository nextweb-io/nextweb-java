package io.nextweb.plugins.core;

import io.nextweb.Entity;
import io.nextweb.operations.EntityMonitorOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Entity_Monitor<EntityType extends Entity> extends
		EntityMonitorOperations, EntityPlugin<EntityType> {

}
