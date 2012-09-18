package io.nextweb.operations;

import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;

public interface EntityRequestOperations extends ExceptionInterceptor {

	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener listener);

}
