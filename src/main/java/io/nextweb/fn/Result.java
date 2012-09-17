package io.nextweb.fn;

public interface Result<ResultType> extends AsyncResult<ResultType> {

	public ResultType get();

}
