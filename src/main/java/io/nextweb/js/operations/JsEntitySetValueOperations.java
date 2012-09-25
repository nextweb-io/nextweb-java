package io.nextweb.js.operations;

import io.nextweb.js.JsEntity;
import io.nextweb.js.fn.JsResult;

public interface JsEntitySetValueOperations<E extends JsEntity<?>> {

	public E setValue(Object newValue);

	public E value(Object newValue);

	public JsResult setValueSafe(Object newValue);
}
