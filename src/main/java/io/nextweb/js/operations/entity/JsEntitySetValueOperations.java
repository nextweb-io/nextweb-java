package io.nextweb.js.operations.entity;

import io.nextweb.js.JsQuery;

public interface JsEntitySetValueOperations {

	public JsQuery setValue(Object newValue);

	public JsQuery value(Object newValue);

	public JsQuery setValueSafe(Object newValue);
}
