package io.nextweb.operations.entity;

import io.nextweb.Entity;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

public interface EntityRemoveOperations {

	/**
	 * Will be executed eagerly.
	 * 
	 * @param entity
	 * @return
	 */
	public Result<Success> remove(Entity entity);

	public Result<Success> removeSafe(Entity entity);

}
