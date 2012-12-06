package io.nextweb.operations.entity;

import io.nextweb.fn.IntegerResult;

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
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @return
	 */
	public IntegerResult clearVersions(int keepVersions);

}
