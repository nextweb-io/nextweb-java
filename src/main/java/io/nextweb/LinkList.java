package io.nextweb;

import io.nextweb.fn.Closure;
import io.nextweb.fn.ReadOnlyList;
import io.nextweb.operations.EntityListRequestOperations;

public interface LinkList extends EntityList, ReadOnlyList<Link>,
		EntityListRequestOperations<LinkList> {

	public LinkList each(Closure<Node> f);

}
