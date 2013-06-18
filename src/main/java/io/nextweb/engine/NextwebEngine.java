package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.operations.exceptions.ExceptionManager;

public interface NextwebEngine extends StartServerCapability {

    public Session createSession();

    public Factory getFactory();

    public ExceptionManager getExceptionManager();

    /**
     * Returns true if this engine supports starting an embedded server.
     * 
     * @return
     */
    public boolean hasStartServerCapability();

    /**
     * Install the selected capability for this engine.
     * 
     * @param capability
     */
    public void injectCapability(Capability capability);

}
