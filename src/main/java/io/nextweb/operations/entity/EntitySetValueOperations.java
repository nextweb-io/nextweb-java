package io.nextweb.operations.entity;

import io.nextweb.Query;

public interface EntitySetValueOperations {

	/**
	 * <p>
	 * Set the value of this node to the specified value.
	 * </p>
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param newValue
	 * @return
	 */
	public Query setValue(Object newValue);

	/**
	 * <p>
	 * Set the value of this node to the specified value.
	 * </p>
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param newValue
	 * @return
	 */
	public Query setValueSafe(Object newValue);

}
