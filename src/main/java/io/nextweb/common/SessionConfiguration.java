package io.nextweb.common;

import io.nextweb.engine.persistence.PersistenceConnection;

public abstract class SessionConfiguration {

	
	public PersistenceConnection connectionToPersistedSessionNetwork() {
		return null;
	}
	
	public PersistenceConnection connectionToPersistedCache() {
		return null;
	}
	
	
}
