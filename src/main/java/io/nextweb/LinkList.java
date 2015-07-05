package io.nextweb;

import delight.functional.Closure;
import delight.functional.collections.ReadOnlyList;

import java.util.List;

import io.nextweb.operations.EntityListRequestOperations;

public interface LinkList extends EntityList, ReadOnlyList<Link>,
		EntityListRequestOperations<LinkList> {

	public LinkList each(Closure<Node> f);

	/**
	 * 
	 * @return The Links in this list.
	 */
	public List<Link> links();

	/**
	 * 
	 * @return The URIs of the Links in this list.
	 */
	public List<String> uris();

}
