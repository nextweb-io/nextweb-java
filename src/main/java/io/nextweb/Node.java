package io.nextweb;

import io.nextweb.operations.entity.EntityRequestOperations;

public interface Node extends Entity, EntityRequestOperations<Node> {

	public String getUri();

	public String uri();

	public String getSecret();

	public String secret();

	public Object value();

	public Object getValue();

	/**
	 * <p>
	 * By default, Nodes are only instantiated if they exist. However, this
	 * method can return false if the node has been removed after it has been
	 * loaded.
	 * </p>
	 * 
	 * @return
	 */
	public boolean exists();

	public <ValueType> ValueType value(Class<ValueType> type);

	public <Type> Type as(final Class<Type> type);

}
