package io.nextweb.operations.entity;

import io.nextweb.Entity;
import io.nextweb.Query;

/**
 * All executed eagerly.
 * 
 * @author mroh004
 * 
 */
public interface EntityAppendOperations {

	/**
	 * <p>
	 * The append operation establishes a connection between two nodes.
	 * </p>
	 * <p>
	 * This basic variant of the append operation will append an arbitrary
	 * <code>value</code> to the specified node.
	 * </p>
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public Query append(Object value);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atAddress
	 * @return
	 */
	public Query append(Object value, String atAddress);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public Query appendValue(Object value);

	/**
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
	public Query append(Entity entity);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public Query appendSafe(Object value);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atAddress
	 * @return
	 */
	public Query appendSafe(Object value, String atAddress);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public Query appendValueSafe(Object value);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param entity
	 * @return
	 */
	public Query appendSafe(Entity entity);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atIndex
	 * @return
	 */
	public Query insert(Object value, int atIndex);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atAddress
	 * @param atIndex
	 * @return
	 */
	public Query insert(Object value, String atAddress, int atIndex);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atIndex
	 * @return
	 */
	public Query insertValue(Object value, int atIndex);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param entity
	 * @param atIndex
	 * @return
	 */
	public Query insert(Entity entity, int atIndex);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atIndex
	 * @return
	 */
	public Query insertSafe(Object value, int atIndex);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atAddress
	 * @param atIndex
	 * @return
	 */
	public Query insertSafe(Object value, String atAddress, int atIndex);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param value
	 * @param atIndex
	 * @return
	 */
	public Query insertValueSafe(Object value, int atIndex);

	/**
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param entity
	 * @param atIndex
	 * @return
	 */
	public Query insertSafe(Entity entity, int atIndex);

}
