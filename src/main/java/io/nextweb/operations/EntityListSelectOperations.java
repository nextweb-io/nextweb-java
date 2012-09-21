package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.NodeListQuery;

public interface EntityListSelectOperations {

	public NodeListQuery select(Link propertyType);

	public NodeListQuery selectAll(Link propertyType);

	public LinkListQuery selectAllLinks();

	public NodeListQuery selectAll();

}
