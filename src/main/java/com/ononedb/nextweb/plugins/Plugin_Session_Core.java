package com.ononedb.nextweb.plugins;

import io.nextweb.Session;
import io.nextweb.operations.SessionOperations;
import io.nextweb.plugins.Plugin;

/**
 * Plugin to support all core session operations.
 * 
 * @author <a href="http://www.mxro.de/">Max Rohde</a>
 * 
 */
public interface Plugin_Session_Core<SessionType extends Session> extends
		Plugin<SessionType>, SessionOperations {

}
