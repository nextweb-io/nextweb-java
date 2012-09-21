package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;
import io.nextweb.operations.EntityListRequestOperations;

public interface NodeList extends EntityList<NodeList>, ReadOnlyList<Node>,
		EntityListRequestOperations<NodeList> {

	public NodeList each(Closure<Node> f);

}
