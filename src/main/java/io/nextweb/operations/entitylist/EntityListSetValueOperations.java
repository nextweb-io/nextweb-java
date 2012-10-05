package io.nextweb.operations.entitylist;

import io.nextweb.ListQuery;

public interface EntityListSetValueOperations {

	public ListQuery setValue(Object newValue);

	public ListQuery setValueSafe(Object newValue);

}
