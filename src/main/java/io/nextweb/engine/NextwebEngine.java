package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.common.LocalServer;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;

import com.ononedb.nextweb.plugins.DefaultPluginFactory;

public interface NextwebEngine {

	public Session createSession();

	public LocalServer startServer(int port);

	public <ResultType> Result<ResultType> createResult(
			ExceptionManager exceptionManager, Session session,
			AsyncResult<ResultType> asyncResult);

	public ExceptionManager getExceptionManager();

	public DefaultPluginFactory plugin();

}
