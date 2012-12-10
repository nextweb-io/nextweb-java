package io.nextweb.operations.entity;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Query;
import io.nextweb.fn.BooleanResult;

public interface EntitySelectOperations {

	/**
	 * <p>
	 * Select the first child node with the specified property type.
	 * </p>
	 * <p>
	 * For more information on this operation, please see the Nextweb
	 * documentation: <a
	 * href="http://nextweb.io/docs/nextweb-select-property.value.html">select
	 * (property)</a>
	 * </p>
	 * 
	 * @param propertyType
	 * @return
	 */
	public Query select(Link propertyType);

	/**
	 * <p>
	 * Tries to find a child with the given propertyType value.
	 * </p>
	 * <p>
	 * If no child is available, a node will be appended with the given
	 * defaultValue.
	 * </p>
	 * 
	 * @param propertyType
	 * @param defaultValue
	 * @return
	 */
	// public Query select(Link propertyType, Object defaultValue);

	/**
	 * <p>
	 * Query for a node at the specified path relative to this node.
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	// public Query select(String path);

	/**
	 * <p>
	 * Query for a node at the specified path.
	 * </p>
	 * <p>
	 * If no node exists, insert a new node at the specified location with the
	 * given defaultValue.
	 * </p>
	 * 
	 * @param path
	 * @param defaultValue
	 * @return
	 */
	// public Query select(String path, Object defaultValue);

	public ListQuery selectAll(Link propertyType);

	public LinkListQuery selectAllLinks();

	public ListQuery selectAll();

	public BooleanResult has(Link propertyType);

	/**
	 * Not supported yet.
	 * 
	 * @param propertyType
	 * @return
	 */
	public Query ifHas(Link propertyType);

}
