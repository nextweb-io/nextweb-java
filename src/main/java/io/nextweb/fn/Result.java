package io.nextweb.fn;

public interface Result<ResultType> extends AsyncResult<ResultType>,
		ExceptionInterceptor {

	public ResultType get();

}
