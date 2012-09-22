package io.nextweb.operations.exceptions;

import io.nextweb.Session;
import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.fn.ExceptionListener;

public class ExceptionManager implements
		ExceptionInterceptor<ExceptionManager>,
		AuthorizationExceptionInterceptor<ExceptionManager>, ExceptionListener,
		AuthorizationExceptionListener, UndefinedExceptionListener,
		UndefinedExceptionInterceptor<ExceptionManager> {

	private final ExceptionManager parentExceptionListener;
	private final Session session;

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
		return this.exceptionListener != null
				|| parentExceptionListener != null
				&& parentExceptionListener.canCatchExceptions()
				|| (session != null && session.getExceptionManager()
						.canCatchExceptions());
	}

	public boolean canCatchUndefinedExceptions() {
		return this.undefinedExceptionListener != null
				|| (this.parentExceptionListener != null && this.parentExceptionListener
						.canCatchUndefinedExceptions())
				|| (session != null && session.getExceptionManager()
						.canCatchUndefinedExceptions()) || canCatchExceptions();
	}

	public boolean canCatchAuthorizationExceptions() {
		return this.authExceptionListener != null
				|| (this.parentExceptionListener != null && this.parentExceptionListener
						.canCatchAuthorizationExceptions())
				|| (session != null && session.getExceptionManager()
						.canCatchAuthorizationExceptions())
				|| canCatchExceptions();
	}

	@Override
	public void onFailure(Object origin, Throwable t) {
		assert canCatchExceptions();

		if (this.exceptionListener != null) {
			this.exceptionListener.onFailure(origin, t);
			return;
		}

		if (parentExceptionListener != null) {
			parentExceptionListener.onFailure(origin, t);
			return;
		}

		if (this.session != null
				&& this.session.getExceptionManager().canCatchExceptions()) {
			this.session.getExceptionManager().onFailure(origin, t);
			return;
		}

		throw new RuntimeException("Unhandled exception in ExceptionManager: "
				+ t.getMessage() + " from class: " + origin.getClass(), t);
		// Nextweb.unhandledException(origin, t);
	}

	@Override
	public void onUnauthorized(Object origin, AuthorizationExceptionResult r) {
		assert canCatchAuthorizationExceptions();

		if (this.authExceptionListener != null) {
			this.authExceptionListener.onUnauthorized(origin, r);
			return;
		}

		if (parentExceptionListener != null) {
			parentExceptionListener.onUnauthorized(origin, r);
			return;
		}

		if (this.session != null
				&& this.session.getExceptionManager()
						.canCatchAuthorizationExceptions()) {
			this.session.getExceptionManager().onUnauthorized(origin, r);
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
	public void onUndefined(Object origin, String message) {
		assert canCatchUndefinedExceptions();

		if (this.undefinedExceptionListener != null) {
			this.undefinedExceptionListener.onUndefined(origin, message);
			return;
		}

		if (parentExceptionListener != null) {
			parentExceptionListener.onUndefined(origin, message);
			return;
		}

		if (this.session != null
				&& this.session.getExceptionManager()
						.canCatchUndefinedExceptions()) {
			this.session.getExceptionManager().onUndefined(origin, message);
			return;
		}

		onFailure(origin, new Exception(
				"No node matching the specified criteria was defined: "
						+ message));
	}

	public ExceptionManager(ExceptionManager parentExceptionManager,
			Session session) {
		super();
		this.parentExceptionListener = parentExceptionManager;
		this.session = session;
	}

}
