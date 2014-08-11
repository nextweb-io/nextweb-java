package io.nextweb;

import io.nextweb.operations.EntityListRequestOperations;

import java.util.List;

import de.mxro.fn.Closure;
import de.mxro.fn.collections.ReadOnlyList;

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
