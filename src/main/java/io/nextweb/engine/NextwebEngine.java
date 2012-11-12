package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.operations.exceptions.ExceptionManager;

public interface NextwebEngine extends StartServerCapability {

	public Session createSession();

	public Factory getFactory();

	public ExceptionManager getExceptionManager();

	public boolean hasStartServerCapability();

	/**
	 * Install the selected capability for this engine.
	 * 
	 * @param capability
	 */
	public void injectCapability(Capability capability);

}
