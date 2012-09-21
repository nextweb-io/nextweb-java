package io.nextweb.operations.exceptions;

import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.fn.ExceptionListener;

public class ExceptionManager implements
		ExceptionInterceptor<ExceptionManager>,
		AuthorizationExceptionInterceptor, ExceptionListener,
		AuthorizationExceptionListener, UndefinedExceptionListener,
		UndefinedExceptionInterceptor {

	private final ExceptionManager fallback;

	private AuthorizationExceptionListener authExceptionListener;
	private ExceptionListener exceptionListener;
	private UndefinedExceptionListener undefinedExceptionListener;

	@Override
	public ExceptionManager catchAuthorizationExceptions(
			AuthorizationExceptionListener authExceptionListener) {
		this.authExceptionListener = authExceptionListener;
		return this;
	}

	@Override
	public ExceptionManager catchExceptions(ExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
		return this;
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

		throw new RuntimeException("Unhandled exception in ExceptionManager: "
				+ t.getMessage() + " from class: " + origin.getClass(), t);
		// Nextweb.unhandledException(origin, t);
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
	public ExceptionManager catchUndefinedExceptions(
			UndefinedExceptionListener undefinedExceptionListener) {
		this.undefinedExceptionListener = undefinedExceptionListener;
		return this;
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
