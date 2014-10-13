package io.nextweb.common;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.promise.NextwebPromise;
import de.mxro.fn.Success;

/**
 * A local server holding nodes and other data.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
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
