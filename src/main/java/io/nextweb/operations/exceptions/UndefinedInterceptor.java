package io.nextweb.operations.exceptions;

public interface UndefinedInterceptor<ForType> {

	public ForType catchUndefined(UndefinedListener listener);

}
