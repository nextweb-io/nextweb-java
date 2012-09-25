package io.nextweb.operations;

import io.nextweb.Entity;
import io.nextweb.Query;

public interface EntityAppendOperations {

	public Query append(Object value);

	public Query append(Object value, String atAddress);

	public Query appendValue(Object value);

	public <GEntity extends Entity> GEntity append(GEntity entity);

}
