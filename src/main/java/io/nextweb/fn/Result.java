package io.nextweb.fn;

public interface Result<ResultType> {

	public ResultType get();

	public void get(ResultCallback<ResultType> callback);

}
