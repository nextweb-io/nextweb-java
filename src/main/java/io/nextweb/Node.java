package io.nextweb;

import io.nextweb.operations.EntityRequestOperations;

public interface Node extends Entity, EntityRequestOperations<Node> {

	public String getUri();

	public String uri();

	public Object value();

	public Object getValue();

	public <ValueType> ValueType value(Class<ValueType> type);

}
