package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.operations.EntityListRequestOperations;

public interface ListQuery extends EntityList,
		EntityListRequestOperations<ListQuery> {

	public ListQuery each(Closure<Node> f);

}
