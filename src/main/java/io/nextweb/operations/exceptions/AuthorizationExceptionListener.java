package io.nextweb.operations.exceptions;

public interface AuthorizationExceptionListener {

	public void onUnauthorized(AuthorizationExceptionResult r);

}
