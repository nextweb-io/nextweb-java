package io.nextweb.operations;

import io.nextweb.Entity;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

public interface EntitySetValueOperations<E extends Entity> {

	public E setValue(Object newValue);

	public Result<Success> setValueSafe(Object newValue);

}
