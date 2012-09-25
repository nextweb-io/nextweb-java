package io.nextweb;

import io.nextweb.operations.EntitySetValueOperations;
import io.nextweb.operations.entity.EntityRequestOperations;

public interface Query extends Entity, EntityRequestOperations<Query>,
		EntitySetValueOperations<Query> {

}
