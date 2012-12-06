package io.nextweb.operations.entity;

import io.nextweb.Query;

public interface EntityReloadOperations {

	/**
	 * <p>
	 * Reload a node to synchronize changes with server.
	 * </p>
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @return
	 */
	public Query reload();

	/**
	 * <p>
	 * Reload while specifying how many levels of children should also be
	 * reloaded.
	 * </p>
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param reloadDepth
	 *            Allows to specify the levels of children to be reloaded. For
	 *            instance, 1 will reload the node and its direct children. 2
	 *            will reload the children's children as well.
	 * @return
	 */
	public Query reload(int reloadDepth);

}
