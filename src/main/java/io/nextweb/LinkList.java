package io.nextweb;

import io.nextweb.fn.ReadOnlyList;
import io.nextweb.fn.Result;

public interface LinkList extends EntityList, ReadOnlyList<Link>,
		Result<LinkList> {

}
