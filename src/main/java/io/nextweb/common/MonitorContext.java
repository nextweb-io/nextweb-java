package io.nextweb.common;

import io.nextweb.Node;

/**
 * This interface is used when a monitor calls the listener waiting for changed
 * nodes.
 * 
 * @see Monitor
 * @see MonitorOperations
 * 
 * @author Max
 * 
 */
public interface MonitorContext {

	/**
	 * <p>
	 * The monitored node, which is changed.
	 * </p>
	 * <p>
	 * Note that the value of the node might be unchanged. A node is reported as
	 * being changed either if its value is change or a connection has been
	 * added to or removed from the node.
	 * 
	 * @return
	 */
	public Node node();

	/**
	 * 
	 * @return The monitor, which calls the listener.
	 */
	public Monitor monitor();

}
