package io.nextweb.operations.entity;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Query;
import io.nextweb.fn.BooleanResult;

public interface EntitySelectOperations {

	public Query select(Link propertyType);

	public Query ifHas(Link propertyType);

	public ListQuery selectAll(Link propertyType);

	public LinkListQuery selectAllLinks();

	public ListQuery selectAll();

	public BooleanResult has(Link propertyType);

}
