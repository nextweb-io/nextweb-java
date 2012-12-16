package io.nextweb.operations.exceptions;

import io.nextweb.engine.NextwebGlobal;
import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;

public class ExceptionManager implements
		ExceptionInterceptor<ExceptionManager>,
		UnauthorizedInterceptor<ExceptionManager>, ExceptionListener,
		UnauthorizedListener, UndefinedListener, ImpossibleListener,
		ImpossibleInterceptor<ExceptionManager>,
		UndefinedInterceptor<ExceptionManager> {

	private UnauthorizedListener authExceptionListener;
	private ExceptionListener exceptionListener;
	private UndefinedListener undefinedExceptionListener;
	private ImpossibleListener impossibleListener;

	private final ExceptionManager parentExceptionManager;

	@Override
	public ExceptionManager catchUnauthorized(
			final UnauthorizedListener authExceptionListener) {
		this.authExceptionListener = authExceptionListener;
		return this;
	}

	@Override
	public ExceptionManager catchExceptions(
			final ExceptionListener exceptionListener) {
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

	public boolean canCatchImpossibe() {
		return this.impossibleListener != null
				|| canCatchExceptions()
				|| (this.parentExceptionManager != null && this.parentExceptionManager
						.canCatchImpossibe());
	}

	@Override
	public void onFailure(final ExceptionResult r) {
		// assert canCatchExceptions();

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

		NextwebGlobal.getEngine().getExceptionManager().onFailure(r);
	}

	@Override
	public void onUnauthorized(final UnauthorizedResult r) {
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

		onFailure(Fn.exception(r.origin(),
				new Exception("Unauthorized: " + r.getMessage())));
	}

	@Override
	public void onImpossible(final ImpossibleResult ir) {
		assert canCatchImpossibe() || canCatchExceptions();

		if (this.impossibleListener != null) {
			this.impossibleListener.onImpossible(ir);
			return;
		}

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(Fn.exception(ir.origin(),
					new Exception("Operation impossible: [" + ir.message()
							+ "]")));
			return;
		}

		if (this.parentExceptionManager != null) {
			if (this.parentExceptionManager.canCatchImpossibe()) {
				this.parentExceptionManager.onImpossible(ir);
				return;
			}
		}

		onFailure(Fn.exception(ir.origin(), new Exception(
				"Operation impossible: [" + ir.message() + "]")));
	}

	@Override
	public ExceptionManager catchImpossible(final ImpossibleListener listener) {

		this.impossibleListener = listener;
		return this;
	}

	@Override
	public ExceptionManager catchUndefined(
			final UndefinedListener undefinedExceptionListener) {
		this.undefinedExceptionListener = undefinedExceptionListener;
		return this;
	}

	@Override
	public void onUndefined(final UndefinedResult r) {
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

		onFailure(Fn.exception(r.origin(),
				new Exception("Undefined: " + r.message())));
	}

	public ExceptionManager(final ExceptionManager parentExceptionManager) {
		super();
		this.parentExceptionManager = parentExceptionManager;

	}

}
