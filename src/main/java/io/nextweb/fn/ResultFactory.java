package io.nextweb.fn;

public interface ResultFactory<ResultType> {

	public Result<ResultType> createResult(AsyncResult<ResultType> asyncResult);

}
