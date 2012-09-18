package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;

public interface NextwebEngine {

	public Session createSession();

	public <ResultType> Result<ResultType> createResult(
			AsyncResult<ResultType> asyncResult);

	public ExceptionManager getExceptionManager();

}
