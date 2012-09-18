package io.nextweb.operations;

import io.nextweb.fn.ExceptionInterceptor;

public interface EntityRequestOperations extends ExceptionInterceptor {

	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener listener);

}
