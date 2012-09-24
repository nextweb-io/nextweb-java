package io.nextweb.operations.entity;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;

public interface EntitySelectOperations {

	public Query select(Link propertyType);

	public NodeListQuery selectAll(Link propertyType);

	public LinkListQuery selectAllLinks();

	public NodeListQuery selectAll();

}
