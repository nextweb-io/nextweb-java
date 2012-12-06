package io.nextweb.operations.entitylist;

import io.nextweb.ListQuery;

public interface EntityListSetValueOperations {

	/**
	 * <p>
	 * Set the value for all elements in this list to the specified value.
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
	public ListQuery setValue(Object newValue);

	/**
	 * <p>
	 * Set the value for all elements in this list to the specified value.
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
	public ListQuery setValueSafe(Object newValue);

}
