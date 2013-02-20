package io.nextweb.operations.entity;

import io.nextweb.Entity;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Query;

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
    public Query select(Entity propertyType);

    /**
     * <p>
     * Tries to find a child with the given propertyType value.
     * </p>
     * <p>
     * If no child is available, a node will be appended with the given
     * defaultValue.
     * </p>
     * <p>
     * WARNING: This operation might trigger an implicit commit if the node to
     * be selected must be created.
     * </p>
     * 
     * @param propertyType
     * @param defaultValue
     * @return
     */
    public Query select(Entity propertyType, Object defaultValue);

    /**
     * <p>
     * Query for a node at the specified path relative to this node.
     * </p>
     * 
     * @param path
     * @return
     */
    public Query select(String path);

    /**
     * <p>
     * Query for a node at the specified path.
     * </p>
     * <p>
     * If no node exists, insert a new node at the specified location with the
     * given defaultValue.
     * </p>
     * <p>
     * WARNING: This operation might trigger an implicit commit if the node in
     * question must be created.
     * </p>
     * 
     * @param path
     * @param defaultValue
     * @return
     */
    public Query select(String path, Object defaultValue);

    public ListQuery selectAll(Entity propertyType);

    public LinkListQuery selectAllLinks();

    public ListQuery selectAll();

}
