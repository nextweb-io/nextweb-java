package io.nextweb.common;

import delight.functional.Success;
import io.nextweb.promise.NextwebPromise;

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
    public NextwebPromise<Success> stop();

}
