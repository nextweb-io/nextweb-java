package io.nextweb.common;

import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

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
