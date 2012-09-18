package io.nextweb;

import io.nextweb.fn.Result;
import io.nextweb.operations.EntityOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface Entity extends HasPlugins, EntityOperations, Result<Node> {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
