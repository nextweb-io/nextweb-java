package io.nextweb.operations.exceptions;

public interface UndefinedExceptionInterceptor<ForType> {

	public ForType catchUndefinedExceptions(UndefinedExceptionListener listener);

}
