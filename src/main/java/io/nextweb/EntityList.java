package io.nextweb;

import io.nextweb.operations.EntityListOperations;
import io.nextweb.plugins.HasPlugins;
import io.nextweb.promise.BasicPromise;
import io.nextweb.promise.exceptions.NextwebExceptionManager;

public interface EntityList extends HasPlugins<EntityList>,
		EntityListOperations, BasicPromise<NodeList> {

	public Session getSession();

	public NextwebExceptionManager getExceptionManager();

}
