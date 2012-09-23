package io.nextweb.operations.exceptions;

import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.fn.ExceptionListener;

public class ExceptionManager implements
		ExceptionInterceptor<ExceptionManager>,
		AuthorizationExceptionInterceptor<ExceptionManager>, ExceptionListener,
		AuthorizationExceptionListener, UndefinedExceptionListener,
		UndefinedExceptionInterceptor<ExceptionManager> {

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

	public boolean canCatchExceptions() {
		return this.exceptionListener != null;

	}

	public boolean canCatchUndefinedExceptions() {
		return this.undefinedExceptionListener != null;

	}

	public boolean canCatchAuthorizationExceptions() {
		return this.authExceptionListener != null;

	}

	@Override
	public void onFailure(Object origin, Throwable t) {
		assert canCatchExceptions();

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(origin, t);
			return;
		}

	}

	@Override
	public void onUnauthorized(Object origin, AuthorizationExceptionResult r) {
		assert canCatchAuthorizationExceptions();

		if (this.authExceptionListener != null) {
			this.authExceptionListener.onUnauthorized(origin, r);
			return;
		}
	}

	@Override
	public ExceptionManager catchUndefinedExceptions(
			UndefinedExceptionListener undefinedExceptionListener) {
		this.undefinedExceptionListener = undefinedExceptionListener;
		return this;
	}

	@Override
	public void onUndefined(Object origin, String message) {
		assert canCatchUndefinedExceptions();

		if (this.undefinedExceptionListener != null) {
			this.undefinedExceptionListener.onUndefined(origin, message);
			return;
		}

	}

	public ExceptionManager() {
		super();

	}

}
