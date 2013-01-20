package io.nextweb.operations.entity;

import io.nextweb.common.MonitorResult;

public interface EntityMonitorOperations {

    /**
     * <p>
     * Monitor a node for changes in a given interval.</
     * <p>
     * This operation is executed eagerly (see <a
     * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
     * and Eager operations</a>).
     * </p>
     * 
     * @return
     */
    public MonitorResult monitor();

}
