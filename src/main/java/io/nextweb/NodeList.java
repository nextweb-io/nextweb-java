package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;
import io.nextweb.fn.Result;

public interface NodeList extends EntityList, ReadOnlyList<Node>,
		Result<NodeList> {

	public NodeList each(Closure<Node> f);

}
