package io.nextweb.fn;

import io.nextweb.operations.exceptions.AllInterceptor;

public interface Result<ReturnType> extends BasicResult<ReturnType>,
		AllInterceptor<Result<ReturnType>> {

	public AsyncResult<ReturnType> getDecoratedResult();

}
