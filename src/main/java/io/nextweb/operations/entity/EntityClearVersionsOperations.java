package io.nextweb.operations.entity;

import io.nextweb.fn.Result;

public interface EntityClearVersionsOperations {

	/**
	 * <p>
	 * Will clear the versions cache kept in remote partners (e.g. in the
	 * cloud).
	 * </p>
	 * <p>
	 * Returns the number of versions, which were cleared on the server, if
	 * successful.
	 * </p>
	 * <p>
	 * Executed eagerly.
	 * </p>
	 * 
	 * @return
	 */
	public Result<Integer> clearVersions(int keepVersions);

}
