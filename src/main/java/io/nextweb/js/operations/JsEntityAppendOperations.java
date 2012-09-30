package io.nextweb.js.operations;

public interface JsEntityAppendOperations {

	public Object append(Object value);

	public Object append(Object value, String atAddress);

	public Object appendValue(Object value);

	public Object appendSafe(Object value);

	public Object appendSafe(Object value, String atAddress);

	public Object appendValueSafe(Object value);

	public Object insert(Object value, int atIndex);

	public Object insert(Object value, String atAddress, int atIndex);

	public Object insertValue(Object value, int atIndex);

	public Object insertSafe(Object value, int atIndex);

	public Object insertSafe(Object value, String atAddress, int atIndex);

	public Object insertValueSafe(Object value, int atIndex);

}
