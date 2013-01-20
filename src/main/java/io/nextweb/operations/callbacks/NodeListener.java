package io.nextweb.operations.callbacks;

import io.nextweb.common.MonitorContext;

public interface NodeListener {

    /**
     * Called when a node value is changed and/or connections are changed.
     * 
     * @param context
     */
    public void onWhenNodeChanged(MonitorContext context);

}
