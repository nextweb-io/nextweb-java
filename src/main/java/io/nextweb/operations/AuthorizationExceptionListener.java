package io.nextweb.operations;

public interface AuthorizationExceptionListener {

	public void onUnauthorized(AuthorizationExceptionResult r);

}
