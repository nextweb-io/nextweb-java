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
     * Adds or updates a node on the storage medium.
     * 
     * @param uri
     * @param node
     */
    public void putNode(String uri, PersistedNode node);

    /**
     * Retrieves a node from the storage medium.
     * 
     * @param uri
     * @return
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
     * @param whenClosed
     */
    public void close(WhenClosed whenClosed);

    /**
     * Writes all changes to the persistence medium.
     * 
     * @param whenCommitted
     */
    public void commit(WhenCommitted whenCommitted);

    /**
     * If this connection keeps a cache in memory, calling this method will
     * clear this cache.
     */
    public void clearCache();

}
