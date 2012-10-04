package io.nextweb.operations.exceptions;

public interface LoginFailuresInterceptor<ReturnType> {

	public ReturnType catchLoginFailures(LoginFailuresListener listener);

}
