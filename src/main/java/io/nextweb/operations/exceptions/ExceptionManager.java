package io.nextweb.operations.exceptions;

import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;

public class ExceptionManager implements
		ExceptionInterceptor<ExceptionManager>,
		UnauthorizedInterceptor<ExceptionManager>, ExceptionListener,
		UnauthorizedListener, UndefinedListener,
		UndefinedInterceptor<ExceptionManager> {

	private UnauthorizedListener authExceptionListener;
	private ExceptionListener exceptionListener;
	private UndefinedListener undefinedExceptionListener;
	private final ExceptionManager parentExceptionManager;

	@Override
	public ExceptionManager catchUnauthorized(
			UnauthorizedListener authExceptionListener) {
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
	public void onFailure(ExceptionResult r) {
		assert canCatchExceptions();

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(r);
			return;
		}

		if (this.parentExceptionManager != null) {
			if (this.parentExceptionManager.canCatchExceptions()) {
				this.parentExceptionManager.onFailure(r);
				return;
			}
		}

		throw new RuntimeException("Unhandled exception from: " + r.origin(),
				r.exception());

	}

	@Override
	public void onUnauthorized(UnauthorizedResult r) {
		assert canCatchAuthorizationExceptions() || canCatchExceptions();

		if (this.authExceptionListener != null) {
			this.authExceptionListener.onUnauthorized(r);
			return;
		}

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(Fn.exception(r.origin(),
					new Exception("Unauthorized: " + r.getMessage())));
			return;
		}

		if (this.parentExceptionManager != null) {
			if (this.parentExceptionManager.canCatchAuthorizationExceptions()) {
				this.parentExceptionManager.onUnauthorized(r);
				return;
			}
		}
	}

	@Override
	public ExceptionManager catchUndefined(
			UndefinedListener undefinedExceptionListener) {
		this.undefinedExceptionListener = undefinedExceptionListener;
		return this;
	}

	@Override
	public void onUndefined(UndefinedResult r) {
		assert canCatchUndefinedExceptions() || canCatchExceptions();

		if (this.undefinedExceptionListener != null) {
			this.undefinedExceptionListener.onUndefined(r);
			return;
		}

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(Fn.exception(r.origin(),
					new Exception("Undefined: " + r.message())));
			return;
		}

		if (this.parentExceptionManager != null) {
			if (this.parentExceptionManager.canCatchUndefinedExceptions()) {
				this.parentExceptionManager.onUndefined(r);
				return;
			}
		}

	}

	public ExceptionManager(ExceptionManager parentExceptionManager) {
		super();
		this.parentExceptionManager = parentExceptionManager;

	}

}
