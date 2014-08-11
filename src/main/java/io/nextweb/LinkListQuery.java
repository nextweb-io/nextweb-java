package io.nextweb;

import io.nextweb.operations.EntityListOperations;
import io.nextweb.operations.EntityListRequestOperations;
import io.nextweb.plugins.HasPlugins;
import io.nextweb.promise.BasicResult;
import io.nextweb.promise.exceptions.ExceptionManager;

public interface LinkListQuery extends BasicResult<LinkList>,
		EntityListOperations, EntityListRequestOperations<LinkListQuery>,
		HasPlugins<LinkListQuery> {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
