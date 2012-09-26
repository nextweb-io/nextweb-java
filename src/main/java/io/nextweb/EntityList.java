package io.nextweb;

import io.nextweb.fn.Result;
import io.nextweb.operations.EntityListOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface EntityList extends HasPlugins<EntityList>,
		EntityListOperations, Result<NodeList> {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
