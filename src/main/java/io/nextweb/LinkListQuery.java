package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.operations.EntityListRequestOperations;

public interface LinkListQuery extends EntityList<LinkList>, Result<LinkList>,
		EntityListRequestOperations<LinkListQuery> {

	public LinkListQuery each(Closure<Node> f);

}
