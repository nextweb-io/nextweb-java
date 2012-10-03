package io.nextweb.fn;

import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;

/**
 * <p>
 * A bare bone version of result objects, which does not allow to specify catch*
 * exception operations.
 * </p>
 * 
 * @author Max Rohde
 * 
 * @param <ResultType>
 */
public interface BasicResult<ResultType> extends AsyncResult<ResultType> {

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
