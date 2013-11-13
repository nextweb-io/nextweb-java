package io.nextweb.common;

import io.nextweb.engine.persistence.PersistenceConnection;

public abstract class SessionConfiguration {

	public static SessionConfiguration withPersistedSession(
			final PersistenceConnection connection) {
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

}
