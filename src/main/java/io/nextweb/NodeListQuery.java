package io.nextweb;

import io.nextweb.fn.Closure;

public interface NodeListQuery extends EntityList<NodeList> {

	public NodeListQuery each(Closure<Node> f);

}
