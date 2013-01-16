package io.nextweb.operations;

import io.nextweb.Query;

public interface EntityShieldOperations {

    /**
     * Retrieve this node in a safe fashion. This is required when this node is
     * likely concurrently changed in other locations. Then, doing changes in
     * the callback of shield assures that no synchronization conflicts occur.
     * 
     * @return
     */
    public Query shield();

}
