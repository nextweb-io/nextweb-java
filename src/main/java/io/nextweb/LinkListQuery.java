package io.nextweb;

import io.nextweb.fn.BasicResult;
import io.nextweb.fn.exceptions.ExceptionManager;
import io.nextweb.operations.EntityListOperations;
import io.nextweb.operations.EntityListRequestOperations;
import io.nextweb.plugins.HasPlugins;

public interface LinkListQuery extends BasicResult<LinkList>,
		EntityListOperations, EntityListRequestOperations<LinkListQuery>,
		HasPlugins<LinkListQuery> {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
