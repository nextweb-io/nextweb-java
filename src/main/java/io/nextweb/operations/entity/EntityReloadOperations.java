package io.nextweb.operations.entity;

import io.nextweb.Query;

public interface EntityReloadOperations {

	public Query reload();

	/**
	 * Reload while specifiying how many levels of children should also be
	 * reloaded.
	 * 
	 * @param reloadDepth
	 *            Allows to specify the levels of children to be reloaded. For
	 *            instance, 1 will reload the node and its direct children. 2
	 *            will reload the children's children as well.
	 * @return
	 */
	public Query reload(int reloadDepth);

}
