package io.nextweb.operations.exceptions;

import io.nextweb.Nextweb;
import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.fn.ExceptionListener;

public class ExceptionManager implements ExceptionInterceptor,
		AuthorizationExceptionInterceptor, ExceptionListener,
		AuthorizationExceptionListener {

	private AuthorizationExceptionListener authExceptionListener;
	private ExceptionListener exceptionListener;

	@Override
	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener authExceptionListener) {
		this.authExceptionListener = authExceptionListener;
	}

	@Override
	public void catchExceptions(ExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}

	@Override
	public void onFailure(Object origin, Throwable t) {
		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(origin, t);
			return;
		}
		Nextweb.unhandledException(origin, t);
	}

	@Override
	public void onUnauthorized(Object origin, AuthorizationExceptionResult r) {
		if (this.authExceptionListener != null) {
			this.authExceptionListener.onUnauthorized(origin, r);
			return;
		}
		onFailure(origin,
				new Exception("Invalid authorization: " + r.getMessage()
						+ " type " + r.getType()));
	}

}
