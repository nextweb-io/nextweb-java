package io.nextweb;

import delight.functional.Closure;
import delight.functional.collections.ReadOnlyList;

import java.util.List;

import io.nextweb.operations.EntityListRequestOperations;

public interface NodeList extends EntityList, ReadOnlyList<Node>,
		EntityListRequestOperations<NodeList> {

	public NodeList each(Closure<Node> f);

	/**
	 * {@link String}: The concatenated node.as(String.class) of all children.
	 * {@link Integer}: The sum of all node.as(Integer.class).
	 * 
	 * @param type
	 * @return
	 */
	public <Type> Type as(Class<Type> type);

	public List<Object> values();

	public List<Node> nodes();
	
	public List<String> uris();

}
