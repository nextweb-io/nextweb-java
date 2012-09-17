package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;

public interface NextwebEngine {

	public Session createSession();

	public void unhandledException(Object context, Throwable t);

	public <ResultType> Result<ResultType> createResult(
			AsyncResult<ResultType> asyncResult);

}
