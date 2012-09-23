package io.nextweb.operations.callbacks;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;

public abstract class EagerCallback<ResultType> implements Callback<ResultType> {

	private boolean hasEagerFailureListener;
	private boolean hasEagerUndefinedListener;
	private boolean hasEagerUnauthorizedListener;
	private ExceptionListener exceptionListener;
	private final Session session;
	private final ExceptionManager exceptionManager;
	private AuthorizationExceptionListener authExceptionListener;
	private UndefinedExceptionListener undefinedExceptionListenr;

	public EagerCallback<ResultType> catchFailures(
			ExceptionListener exceptionListener) {
		hasEagerFailureListener = true;
		this.exceptionListener = exceptionListener;
		return this;
	}

	public EagerCallback<ResultType> catchAuthorizationExceptions(
			AuthorizationExceptionListener exceptionListener) {
		hasEagerUnauthorizedListener = true;
		this.authExceptionListener = exceptionListener;
		return this;
	}

	public EagerCallback<ResultType> catchUndefinedExceptions(
			UndefinedExceptionListener listener) {
		hasEagerUndefinedListener = true;
		this.undefinedExceptionListenr = listener;
		return this;
	}

	@Override
	public final void onFailure(Object origin, Throwable t) {
		if (hasEagerFailureListener) {
			this.exceptionListener.onFailure(origin, t);
			return;
		}

		if (exceptionManager.canCatchExceptions()) {
			exceptionManager.onFailure(origin, t);
			return;
		}

		if (session.getExceptionManager().canCatchExceptions()) {
			session.getExceptionManager().onFailure(origin, t);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onFailure(origin, t);

	}

	@Override
	public final void onUnauthorized(Object origin,
			AuthorizationExceptionResult r) {
		if (hasEagerUnauthorizedListener) {
			this.authExceptionListener.onUnauthorized(origin, r);
			return;
		}

		if (exceptionManager.canCatchAuthorizationExceptions()) {
			exceptionManager.onUnauthorized(origin, r);
			return;
		}

		if (session.getExceptionManager().canCatchAuthorizationExceptions()) {
			session.getExceptionManager().onUnauthorized(origin, r);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onUnauthorized(origin, r);
	}

	@Override
	public final void onUndefined(Object origin, String message) {
		if (hasEagerUndefinedListener) {
			this.undefinedExceptionListenr.onUndefined(origin, message);
			return;
		}

		if (exceptionManager.canCatchUndefinedExceptions()) {
			exceptionManager.onUndefined(origin, message);
			return;
		}

		if (session.getExceptionManager().canCatchUndefinedExceptions()) {
			session.getExceptionManager().onUndefined(origin, message);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onUndefined(origin, message);
	}

	@Override
	public boolean hasEagerFailureListener() {
		return hasEagerFailureListener;
	}

	@Override
	public boolean hasEagerUndefinedListener() {

		return hasEagerUndefinedListener;
	}

	@Override
	public boolean hasEagerUnauthorizedListener() {

		return hasEagerUnauthorizedListener;
	}

	public EagerCallback(Session session, ExceptionManager exceptionManager) {
		super();
		this.session = session;
		this.exceptionManager = exceptionManager;
	}

}
