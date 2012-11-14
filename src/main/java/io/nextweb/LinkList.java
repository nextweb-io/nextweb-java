package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;
import io.nextweb.operations.EntityListRequestOperations;

import java.util.List;

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
