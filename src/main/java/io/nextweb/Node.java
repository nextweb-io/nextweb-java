package io.nextweb;

import io.nextweb.operations.entity.EntityRequestOperations;

public interface Node extends Entity, EntityRequestOperations<Node> {

	public String getUri();

	public String uri();

	/**
	 * <p>
	 * Will load any changes for the node from the server.
	 * </p>
	 * 
	 * <p>
	 * Executed eagerly.
	 * </p>
	 * 
	 * @return
	 */
	public Query reload();

	public String getSecret();

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

}
