package io.nextweb.operations.entity;

import io.nextweb.Entity;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

public interface EntityRemoveOperations {

	/**
	 * <p>
	 * Remove a connection between nodes.
	 * </p>
	 * 
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param entity
	 * @return
	 */
	public Result<Success> remove(Entity entity);

	/**
	 * <p>
	 * Remove a connection between nodes.
	 * </p>
	 * 
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param entity
	 * @return
	 */
	public Result<Success> removeSafe(Entity entity);

}
