package io.nextweb;

import io.nextweb.operations.EntityOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface EntityList extends HasPlugins<EntityList>, EntityOperations {

	public Session getSession();

	public ExceptionManager getExceptionManager();

}
