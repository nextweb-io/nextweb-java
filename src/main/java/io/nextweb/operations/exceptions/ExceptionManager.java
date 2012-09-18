package io.nextweb.operations.exceptions;

import io.nextweb.Nextweb;
import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.fn.ExceptionListener;

public class ExceptionManager implements ExceptionInterceptor,
		AuthorizationExceptionInterceptor, ExceptionListener,
		AuthorizationExceptionListener, UndefinedExceptionListener,
		UndefinedExceptionInterceptor {

	private final ExceptionManager fallback;

	private AuthorizationExceptionListener authExceptionListener;
	private ExceptionListener exceptionListener;
	private UndefinedExceptionListener undefinedExceptionListener;

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

		if (fallback != null) {
			fallback.onFailure(origin, t);
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

		if (fallback != null) {
			fallback.onUnauthorized(origin, r);
			return;
		}

		onFailure(origin,
				new Exception("Invalid authorization: " + r.getMessage()
						+ " type " + r.getType()));
	}

	@Override
	public void catchUndefinedExceptions(
			UndefinedExceptionListener undefinedExceptionListener) {
		this.undefinedExceptionListener = undefinedExceptionListener;
	}

	@Override
	public void onUndefined(Object origin) {
		if (this.undefinedExceptionListener != null) {
			this.undefinedExceptionListener.onUndefined(origin);
			return;
		}

		if (fallback != null) {
			fallback.onUndefined(origin);
			return;
		}

		onFailure(origin, new Exception(
				"No node matching the specified criteria was defined."));
	}

	public ExceptionManager(ExceptionManager fallback) {
		super();
		this.fallback = fallback;
	}

}
