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
	 * 
	 * @param value
	 * @return
	 */
	public Query append(Object value);

	public Query append(Object value, String atAddress);

	public Query appendValue(Object value);

	public Query append(Entity entity);

	public Query appendSafe(Object value);

	public Query appendSafe(Object value, String atAddress);

	public Query appendValueSafe(Object value);

	public Query appendSafe(Entity entity);

	public Query insert(Object value, int atIndex);

	public Query insert(Object value, String atAddress, int atIndex);

	public Query insertValue(Object value, int atIndex);

	public Query insert(Entity entity, int atIndex);

	public Query insertSafe(Object value, int atIndex);

	public Query insertSafe(Object value, String atAddress, int atIndex);

	public Query insertValueSafe(Object value, int atIndex);

	public Query insertSafe(Entity entity, int atIndex);

}
