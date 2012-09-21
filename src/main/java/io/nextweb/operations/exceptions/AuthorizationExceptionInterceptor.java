package io.nextweb.operations.exceptions;

public interface AuthorizationExceptionInterceptor<ForType> {

	public ForType catchAuthorizationExceptions(
			AuthorizationExceptionListener listener);

}
