package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.LinkList;

public interface LinkOperations {

	/**
	 * Will turn the current and the specified node into a node list.
	 * 
	 * @param link
	 * @return
	 */
	public LinkList node(Link link);

}
