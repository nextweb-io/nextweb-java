package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.common.SessionConfiguration;
import io.nextweb.fn.exceptions.ExceptionManager;
import de.mxro.factories.FactoryCollection;
import de.mxro.service.ServiceRegistry;

public interface NextwebEngine extends StartServerCapability {

    public Session createSession();

    /**
     * Creates a new session with specific configuration options.
     * 
     * @param connection
     * @return
     */
    public Session createSession(SessionConfiguration configuration);
    
    /**
     * Factory to build nodes.
     * @return
     */
    public Factory getFactory();

    
    /**
     * Registry of factories to build dependencies.
     * 
     * @return
     */
    public FactoryCollection factories();
    
    
    /**
     * Registry of simple services.
     * 
     * @return
     */
    public ServiceRegistry services();
    
    
    /**
     * Manager to catch exceptions globally.
     * @return
     */
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
