package io.nextweb.operations.entity;

import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

public interface EntityClearVersionsOperations {

	/**
	 * <p>
	 * Will clear the versions cache kept in remote partners (e.g. in the
	 * cloud).
	 * </p>
	 * 
	 * 
	 * @return
	 */
	public Result<Success> clearVersions(int keepVersions);

}
