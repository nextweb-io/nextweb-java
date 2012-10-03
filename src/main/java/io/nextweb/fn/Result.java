package io.nextweb.fn;

import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;

public interface Result<ResultType> extends AsyncResult<ResultType> {

	/**
	 * get() will ignore all defined exception interceptors to assure
	 * termination of the statement.
	 * 
	 * @return
	 */
	public ResultType get();

	public void get(Closure<ResultType> callback);

	public ExceptionManager getExceptionManager();

	@Override
	public void get(Callback<ResultType> callback);

}
