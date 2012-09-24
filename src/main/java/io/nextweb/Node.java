package io.nextweb;

import io.nextweb.operations.NodeAppendOperations;
import io.nextweb.operations.entity.EntityRequestOperations;

public interface Node extends Entity, EntityRequestOperations<Node>,
		NodeAppendOperations {

	public String getUri();

	public String uri();

	public Object value();

	public Object getValue();

	public <ValueType> ValueType value(Class<ValueType> type);

}
