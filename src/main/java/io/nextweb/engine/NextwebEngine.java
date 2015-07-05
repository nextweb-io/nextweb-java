package io.nextweb.engine;

import de.mxro.client.Client;
import io.nextweb.Session;
import io.nextweb.common.SessionConfiguration;
import io.nextweb.promise.exceptions.NextwebExceptionManager;

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

    public Client client();

    /**
     * Manager to catch exceptions globally.
     * 
     */
    public NextwebExceptionManager getExceptionManager();

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
