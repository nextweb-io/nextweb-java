package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.common.SessionConfiguration;
import io.nextweb.promise.exceptions.ExceptionManager;
import de.mxro.factories.FactoryCollection;
import de.mxro.service.ServiceRegistry;

public interface NextwebEngine extends StartServerCapability {

    public Session createSession();

    /**
     * Creates a new session with specific configuration options.
     * 
     */
    public Session createSession(SessionConfiguration configuration);

    /**
     * Factory to build nodes.
     * 
     */
    public Factory getFactory();

    /**
     * Registry of factories to build dependencies.
     * 
     */
    public FactoryCollection factories();

    /**
     * Registry of simple services.
     * 
     */
    public ServiceRegistry services();

    /**
     * Manager to catch exceptions globally.
     * 
     */
    public ExceptionManager getExceptionManager();

    /**
     * Returns true if this engine supports starting an embedded server.
     * 
     */
    public boolean hasStartServerCapability();

    /**
     * Returns true of this engine supports storing data replicated on a server
     * on the local machine.
     * 
     */
    public boolean hasPersistedReplicationCapability();

    /**
     * Install the selected capability for this engine.
     * 
     * @param capability
     *            The capability to be injected.
     */
    public void injectCapability(Capability capability);

}
