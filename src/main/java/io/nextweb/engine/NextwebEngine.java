package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.common.SessionConfiguration;
import io.nextweb.fn.exceptions.ExceptionManager;

public interface NextwebEngine extends StartServerCapability {

    public Session createSession();

    /**
     * Creates a new session with specific configuration options.
     * 
     * @param connection
     * @return
     */
    public Session createSession(SessionConfiguration configuration);
    
    public Factory getFactory();

    public ExceptionManager getExceptionManager();

    /**
     * Returns true if this engine supports starting an embedded server.
     * 
     * @return
     */
    public boolean hasStartServerCapability();

    /**
     * Returns true of this engine supports storing data replicated on a server
     * on the local machine.
     * 
     * @return
     */
    public boolean hasPersistedReplicationCapability();

    /**
     * Install the selected capability for this engine.
     * 
     * @param capability
     */
    public void injectCapability(Capability capability);

}
