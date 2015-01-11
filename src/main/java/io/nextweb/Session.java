package io.nextweb;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.operations.SessionOperations;
import io.nextweb.plugins.HasPlugins;
import io.nextweb.promise.NextwebOperation;
import io.nextweb.promise.NextwebPromise;
import io.nextweb.promise.exceptions.ExceptionManager;

public interface Session extends SessionOperations, HasPlugins<Session> {

    public NextwebEngine getEngine();

    public ExceptionManager getExceptionManager();

    public <ResultType> NextwebPromise<ResultType> promise(NextwebOperation<ResultType> asyncResult);

}
