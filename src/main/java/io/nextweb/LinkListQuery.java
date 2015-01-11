package io.nextweb;

import io.nextweb.operations.EntityListOperations;
import io.nextweb.operations.EntityListRequestOperations;
import io.nextweb.plugins.HasPlugins;
import io.nextweb.promise.BasicPromise;
import io.nextweb.promise.exceptions.NextwebExceptionManager;

public interface LinkListQuery extends BasicPromise<LinkList>,
		EntityListOperations, EntityListRequestOperations<LinkListQuery>,
		HasPlugins<LinkListQuery> {

	public Session getSession();

	public NextwebExceptionManager getExceptionManager();

}
