package io.nextweb.operations;

import io.nextweb.Query;

public interface UnresolvedEntityAppendOperations {

	public Query append(Object value);

	public Query append(Object value, String atAddress);

	public Query appendValue(Object value);

}
