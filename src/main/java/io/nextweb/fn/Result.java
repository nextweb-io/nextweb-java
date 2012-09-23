package io.nextweb.fn;

public interface Result<ResultType> extends AsyncResult<ResultType> {

	/**
	 * get() will ignore all defined exception interceptors to assure
	 * termination of the statement.
	 * 
	 * @return
	 */
	public ResultType get();

	public void get(Closure<ResultType> callback);

	// / public boolean available(); ?

}
