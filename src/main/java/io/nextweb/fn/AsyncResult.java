package io.nextweb.fn;

import io.nextweb.operations.callbacks.Callback;

public interface AsyncResult<ResultType> {

	public void get(Callback<ResultType> callback);

}
