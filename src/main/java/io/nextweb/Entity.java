package io.nextweb;

import io.nextweb.operations.EntityOperations;
import io.nextweb.plugins.HasPlugins;
import io.nextweb.promise.BasicPromise;
import io.nextweb.promise.exceptions.ExceptionManager;

public interface Entity extends EntityOperations, BasicPromise<Node>,
        HasPlugins<Entity> {

    public Session getSession();

    @Override
    public ExceptionManager getExceptionManager();

}
