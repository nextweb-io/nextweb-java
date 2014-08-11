package io.nextweb.common;

import io.nextweb.operations.callbacks.NodeListener;
import io.nextweb.promise.NextwebPromise;

public interface MonitorResult extends NextwebPromise<Monitor> {

    /**
     * Set the speed at which messages are sent to the remote server to check
     * for changes.
     * 
     * @param interval
     * @return this monitor result
     */
    public MonitorResult setInterval(Interval interval);

    /**
     * <p>
     * How many child nodes should be monitored as well?
     * <p>
     * Default is 0, just monitoring current node for value changes and
     * added/removed children.
     * <p>
     * 1 will monitor for child node value changes and added/removed children of
     * child nodes etc.
     * 
     * @param depth
     * @return this monitor result
     */
    public MonitorResult setDepth(int depth);

    /**
     * Register a listener to be called when there is a change of node values or
     * connections.
     * 
     * @param whenChanged
     * @return this monitor result
     */
    public MonitorResult addListener(NodeListener listener);

}
