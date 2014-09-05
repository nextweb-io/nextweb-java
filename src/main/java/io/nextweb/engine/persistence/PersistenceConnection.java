/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package io.nextweb.engine.persistence;

import io.nextweb.engine.persistence.v01.PersistedNode;

/**
 * Definition of a connection to a service required to persist a network.
 * 
 * 
 * @author Max Rohde
 */
public interface PersistenceConnection {

    /**
     * <p>
     * Adds or updates a node on the storage medium.
     * <p>
     * This operation should be <b>non-blocking</b>. That is, it should not wait
     * for the write to be performed on the storage medium but collect pending
     * write operations in a cache, which are performed asynchronously.
     * 
     * @param uri
     *            The uri of the node to be stored
     * @param node
     *            The node to be stored
     */
    public void putNode(String uri, PersistedNode node);

    /**
     * <p>
     * Retrieves a node from the storage medium.
     * <p>
     * This operation must be <b>blocking</b>. That is, it should only return
     * after the respective node is retrieved from the storage medium.
     * 
     * @param uri
     *            The uri of the node to be retrieved.
     * @return The node if it was found, otherwise null.
     */
    public PersistedNode getNode(String uri);

    /**
     * Deletes a node from the storage medium.
     * 
     * @param uri
     */
    public void deleteNode(String uri);

    /**
     * Writes all changes to he persistence medium and closes the connection.
     * 
     */
    public void close(WhenClosed whenClosed);

    /**
     * Writes all changes to the persistence medium.
     * 
     */
    public void commit(WhenCommitted whenCommitted);

    /**
     * If this connection keeps a cache in memory, calling this method will
     * clear this cache.
     */
    public void clearCache();

}
