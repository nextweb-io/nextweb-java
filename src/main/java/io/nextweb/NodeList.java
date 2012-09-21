package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;

public interface NodeList extends EntityList, ReadOnlyList<Node> {

	public NodeList each(Closure<Node> f);

}
