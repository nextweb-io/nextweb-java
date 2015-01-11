package io.nextweb;

import io.nextweb.operations.EntityOperations;
import io.nextweb.plugins.HasPlugins;
import io.nextweb.promise.BasicPromise;
import io.nextweb.promise.exceptions.NextwebExceptionManager;

public interface Entity extends EntityOperations, BasicPromise<Node>, HasPlugins<Entity> {

    public Session getSession();

    public Session session();

    @Override
    public NextwebExceptionManager getExceptionManager();

}
