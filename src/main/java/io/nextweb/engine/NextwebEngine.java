package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.core.DefaultPluginFactory;

public interface NextwebEngine {

	public Session createSession();

	public <ResultType> Result<ResultType> createResult(
			ExceptionManager exceptionManager,
			AsyncResult<ResultType> asyncResult);

	public ExceptionManager getExceptionManager();

	public DefaultPluginFactory plugin();

}
