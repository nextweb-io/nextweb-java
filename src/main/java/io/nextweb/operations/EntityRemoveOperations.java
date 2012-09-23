package io.nextweb.operations;

import io.nextweb.Entity;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;

public interface EntityRemoveOperations<EntityType> {

	public Result<SuccessFail> remove(Entity entity);

}
