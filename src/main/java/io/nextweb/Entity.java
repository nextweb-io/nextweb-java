package io.nextweb;

import io.nextweb.fn.Result;
import io.nextweb.operations.EntityOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface Entity extends EntityOperations, Result<Node>,
		HasPlugins<Entity> {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
