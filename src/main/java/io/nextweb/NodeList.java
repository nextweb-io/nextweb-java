package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;

public interface NodeList extends ReadOnlyList<Node> {

	public NodeList each(Closure<Node> f);

}
