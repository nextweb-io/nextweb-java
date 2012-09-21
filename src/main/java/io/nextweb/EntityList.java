package io.nextweb;

import io.nextweb.fn.Result;
import io.nextweb.operations.EntityListSelectOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface EntityList<ResultType extends EntityList<?>> extends
		HasPlugins<EntityList<ResultType>>, EntityListSelectOperations,
		Result<ResultType> {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
