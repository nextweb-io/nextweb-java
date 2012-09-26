package io.nextweb.operations.entitylist;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;

public interface EntityListSelectOperations {

	public ListQuery select(Link propertyType);

	public ListQuery selectAll(Link propertyType);

	public LinkListQuery selectAllLinks();

	public ListQuery selectAll();

}
