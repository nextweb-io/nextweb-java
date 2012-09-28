package io.nextweb;

import io.nextweb.operations.entity.EntityRequestOperations;

public interface Node extends Entity, EntityRequestOperations<Node> {

	public String getUri();

	public String uri();

	public String getSecret();

	public Object value();

	public Object getValue();

	public <ValueType> ValueType value(Class<ValueType> type);

}
