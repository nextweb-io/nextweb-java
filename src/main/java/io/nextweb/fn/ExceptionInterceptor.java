package io.nextweb.fn;

public interface ExceptionInterceptor<ForType> {

	public ForType catchExceptions(ExceptionListener listener);

}
