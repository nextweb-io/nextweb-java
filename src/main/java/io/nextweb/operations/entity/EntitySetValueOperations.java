package io.nextweb.operations.entity;

import io.nextweb.Query;

public interface EntitySetValueOperations {

	public Query setValue(Object newValue);

	public Query setValueSafe(Object newValue);

}
