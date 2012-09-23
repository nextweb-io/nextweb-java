package io.nextweb.operations;

import io.nextweb.Node;

public interface NodeAppendOperations {

	public Node append(Object value);

	public Node append(Object value, String atAddress);

	public Node appendValue(Object value);

}
