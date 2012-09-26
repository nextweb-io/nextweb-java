package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;
import io.nextweb.operations.EntityListRequestOperations;

import java.util.List;

public interface NodeList extends EntityList, ReadOnlyList<Node>,
		EntityListRequestOperations<NodeList> {

	public NodeList each(Closure<Node> f);

	public List<Object> values();

}
