package io.nextweb.fn;

public interface AsyncResult<ResultType> {

	public void get(RequestCallback<ResultType> callback);

}
