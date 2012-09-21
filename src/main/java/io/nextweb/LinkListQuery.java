package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;

public interface LinkListQuery extends EntityList<LinkList>, Result<LinkList> {

	public LinkListQuery each(Closure<Node> f);

}
