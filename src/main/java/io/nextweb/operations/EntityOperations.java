package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.ListQuery;
import io.nextweb.Query;

public interface EntityOperations {

	public Query select(Link propertyType);
	
	public ListQuery selectAll(Link propertyType);
	
	
	
}
