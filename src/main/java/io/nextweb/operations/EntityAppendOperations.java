package io.nextweb.operations;

import io.nextweb.Entity;
import io.nextweb.Query;

/**
 * All executed eagerly.
 * 
 * @author mroh004
 * 
 */
public interface EntityAppendOperations {

	public Query append(Object value);

	public Query append(Object value, String atAddress);

	public Query appendValue(Object value);

	public <GEntity extends Entity> GEntity append(GEntity entity);
	/*
	 * public Query appendSafe(Object value);
	 * 
	 * public Query appendSafe(Object value, String atAddress);
	 * 
	 * public Query appendValueSafe(Object value);
	 * 
	 * public Query appendSafe(Entity entity);
	 */

}
