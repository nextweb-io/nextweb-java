package io.nextweb.operations.exceptions;

public interface AuthorizationExceptionInterceptor {

	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener listener);

}
