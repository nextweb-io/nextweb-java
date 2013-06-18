package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.engine.persistence.PersistenceConnection;

/**
 * 
 * @author Max Rohde
 * 
 */
public interface PersistedReplicationCapability extends Capability {

    /**
     * Creates a new session, for which all data replicated from the server is
     * stored in the defined persistence connection.
     * 
     * @param connection
     * @return
     */
    public Session createSession(PersistenceConnection connection);

}
