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
	private final ExceptionManager parentExceptionManager;

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
		return this.exceptionListener != null
				|| (this.parentExceptionManager != null && this.parentExceptionManager
						.canCatchExceptions());

	}

	public boolean canCatchUndefinedExceptions() {
		return this.undefinedExceptionListener != null
				|| canCatchExceptions()
				|| (this.parentExceptionManager != null && this.parentExceptionManager
						.canCatchUndefinedExceptions());

	}

	public boolean canCatchAuthorizationExceptions() {
		return this.authExceptionListener != null
				|| canCatchExceptions()
				|| (this.parentExceptionManager != null && this.parentExceptionManager
						.canCatchAuthorizationExceptions());

	}

	@Override
	public void onFailure(Object origin, Throwable t) {
		assert canCatchExceptions();

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(origin, t);
			return;
		}

		if (this.parentExceptionManager != null) {
			if (this.parentExceptionManager.canCatchExceptions()) {
				this.parentExceptionManager.onFailure(origin, t);
				return;
			}
		}

	}

	@Override
	public void onUnauthorized(Object origin, AuthorizationExceptionResult r) {
		assert canCatchAuthorizationExceptions() || canCatchExceptions();

		if (this.authExceptionListener != null) {
			this.authExceptionListener.onUnauthorized(origin, r);
			return;
		}

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(origin, new Exception(
					"Unauthorized: " + r.getMessage()));
			return;
		}

		if (this.parentExceptionManager != null) {
			if (this.parentExceptionManager.canCatchAuthorizationExceptions()) {
				this.parentExceptionManager.onUnauthorized(origin, r);
				return;
			}
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
		assert canCatchUndefinedExceptions() || canCatchExceptions();

		if (this.undefinedExceptionListener != null) {
			this.undefinedExceptionListener.onUndefined(origin, message);
			return;
		}

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(origin, new Exception(
					"Undefined: " + message));
			return;
		}

		if (this.parentExceptionManager != null) {
			if (this.parentExceptionManager.canCatchUndefinedExceptions()) {
				this.parentExceptionManager.onUndefined(origin, message);
				return;
			}
		}

	}

	public ExceptionManager(ExceptionManager parentExceptionManager) {
		super();
		this.parentExceptionManager = parentExceptionManager;

	}

}
