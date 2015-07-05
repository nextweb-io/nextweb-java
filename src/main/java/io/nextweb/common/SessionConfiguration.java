package io.nextweb.common;

import de.mxro.async.map.AsyncMap;
import io.nextweb.engine.persistence.PersistenceConnection;

public abstract class SessionConfiguration {

    public static SessionConfiguration withPersistedSession(final PersistenceConnection connection) {
        return new SessionConfiguration() {

            @Override
            public PersistenceConnection connectionToPersistedSessionNetwork() {
                return connection;
            }

        };
    }

    public PersistenceConnection connectionToPersistedSessionNetwork() {
        return null;
    }

    public PersistenceConnection connectionToPersistedCache() {
        return null;
    }

    /**
     * Cache shared between sessions.
     * 
     * @return
     */
    public AsyncMap<String, Object> sharedCache() {
        return null;
    }

}
