package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.operations.EntityListRequestOperations;

public interface NodeListQuery extends EntityList<NodeList>,
		EntityListRequestOperations<NodeListQuery> {

	public NodeListQuery each(Closure<Node> f);

}
