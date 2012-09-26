package io.nextweb;

import io.nextweb.fn.Result;
import io.nextweb.operations.EntityListOperations;
import io.nextweb.operations.EntityListRequestOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface LinkListQuery extends Result<LinkList>, EntityListOperations,
		EntityListRequestOperations<LinkListQuery>, HasPlugins<LinkListQuery> {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
