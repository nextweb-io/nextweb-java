package io.nextweb.fn;

public interface AsyncResult<ResultType> {

	public void get(ResultCallback<ResultType> callback);

}