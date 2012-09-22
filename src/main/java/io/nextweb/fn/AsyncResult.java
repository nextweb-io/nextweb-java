package io.nextweb.fn;

public interface AsyncResult<ResultType> {

	public void get(RequestResultCallback<ResultType> callback);

}
