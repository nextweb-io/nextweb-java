package io.nextweb.common;

import de.mxro.fn.Success;
import io.nextweb.promise.Result;

public interface Monitor {

    /**
     * <p>
     * Stop monitoring for changes of node and child nodes.
     * <p>
     * Result will be available when all pending monitor operations are
     * completed and stopped.
     * 
     * @return this monitor
     */
    public Result<Success> stop();

}
