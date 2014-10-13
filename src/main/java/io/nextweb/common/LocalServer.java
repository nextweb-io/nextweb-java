package io.nextweb.common;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.promise.NextwebPromise;
import de.mxro.fn.Success;

public interface LocalServer {

    /**
     * 
     * @return A nextweb engine which is aware of this local server.
     */
    public NextwebEngine engine();

    /**
     * Finalizes this server and releases any associated resources.
     * 
     * @return A promise which triggers the server shutdown.
     */
    public NextwebPromise<Success> shutdown();

}
