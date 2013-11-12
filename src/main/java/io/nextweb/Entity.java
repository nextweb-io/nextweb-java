package io.nextweb;

import io.nextweb.fn.BasicResult;
import io.nextweb.fn.exceptions.ExceptionManager;
import io.nextweb.operations.EntityOperations;
import io.nextweb.plugins.HasPlugins;

public interface Entity extends EntityOperations, BasicResult<Node>,
        HasPlugins<Entity> {

    public Session getSession();

    @Override
    public ExceptionManager getExceptionManager();

}
