package io.nextweb;

import io.nextweb.fn.BasicResult;
import io.nextweb.operations.EntityOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface Entity extends EntityOperations, BasicResult<Node>,
        HasPlugins<Entity> {

    public Session getSession();

    @Override
    public ExceptionManager getExceptionManager();

}
