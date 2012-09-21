package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;

public interface LinkList extends EntityList<LinkList>, ReadOnlyList<Link> {

	public LinkList each(Closure<Node> f);

}
