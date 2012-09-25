package io.nextweb.operations;

import io.nextweb.Query;

public interface EntitySetValueOperations {

	public Query setValue(Object newValue);

	public Query setValueSafe(Object newValue);

}
