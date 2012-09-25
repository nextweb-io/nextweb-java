package io.nextweb.operations.exceptions;

public interface UnauthorizedInterceptor<ForType> {

	public ForType catchUnauthorized(
			UnauthorizedListener listener);

}
