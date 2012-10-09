package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;

public interface NextwebEngine extends StartServerCapability {

	public Session createSession();

	public <ResultType> Result<ResultType> createResult(
			ExceptionManager exceptionManager, Session session,
			AsyncResult<ResultType> asyncResult);

	public ExceptionManager getExceptionManager();

	// public DefaultPluginFactory plugin();

	public boolean hasStartServerCapability();

	/**
	 * Install the selected capability for this engine.
	 * 
	 * @param capability
	 */
	public void injectCapability(Capability capability);

}
