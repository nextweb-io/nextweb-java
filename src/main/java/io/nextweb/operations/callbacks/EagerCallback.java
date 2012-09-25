package io.nextweb.operations.callbacks;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;

public abstract class EagerCallback<ResultType> implements Callback<ResultType> {

	private boolean hasEagerFailureListener;
	private boolean hasEagerUndefinedListener;
	private boolean hasEagerUnauthorizedListener;
	private ExceptionListener exceptionListener;
	private final Session session;
	private final ExceptionManager exceptionManager;
	private UnauthorizedListener authExceptionListener;
	private UndefinedListener undefinedExceptionListenr;

	public EagerCallback<ResultType> catchFailures(
			ExceptionListener exceptionListener) {
		hasEagerFailureListener = true;
		this.exceptionListener = exceptionListener;
		return this;
	}

	public EagerCallback<ResultType> catchAuthorizationExceptions(
			UnauthorizedListener exceptionListener) {
		hasEagerUnauthorizedListener = true;
		this.authExceptionListener = exceptionListener;
		return this;
	}

	public EagerCallback<ResultType> catchUndefinedExceptions(
			UndefinedListener listener) {
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

		if (session != null
				&& session.getExceptionManager().canCatchExceptions()) {
			session.getExceptionManager().onFailure(origin, t);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onFailure(origin, t);

	}

	@Override
	public final void onUnauthorized(Object origin, UnauthorizedResult r) {
		if (hasEagerUnauthorizedListener) {
			this.authExceptionListener.onUnauthorized(origin, r);
			return;
		}

		if (hasEagerFailureListener) {
			this.exceptionListener.onFailure(origin, new Exception(
					"Unauthorized: " + r.getMessage()));
			return;
		}

		if (exceptionManager.canCatchAuthorizationExceptions()) {
			exceptionManager.onUnauthorized(origin, r);
			return;
		}

		if (session != null
				&& session.getExceptionManager()
						.canCatchAuthorizationExceptions()) {
			session.getExceptionManager().onUnauthorized(origin, r);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onUnauthorized(origin, r);
	}

	@Override
	public final void onUndefined(UndefinedResult r) {
		if (hasEagerUndefinedListener) {
			this.undefinedExceptionListenr.onUndefined(r);
			return;
		}

		if (hasEagerFailureListener) {
			this.exceptionListener.onFailure(r.origin(), new Exception(
					"Undefined: " + r.message()));
			return;
		}

		if (exceptionManager.canCatchUndefinedExceptions()) {
			exceptionManager.onUndefined(r);
			return;
		}

		if (session != null
				&& session.getExceptionManager().canCatchUndefinedExceptions()) {
			session.getExceptionManager().onUndefined(r);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onUndefined(r);
	}

	@Override
	public boolean hasEagerFailureListener() {
		return hasEagerFailureListener;
	}

	@Override
	public boolean hasEagerUndefinedListener() {

		return hasEagerUndefinedListener || hasEagerFailureListener;
	}

	@Override
	public boolean hasEagerUnauthorizedListener() {

		return hasEagerUnauthorizedListener || hasEagerFailureListener;
	}

	public EagerCallback(Session session, ExceptionManager exceptionManager) {
		super();
		this.session = session;
		this.exceptionManager = exceptionManager;
	}

}
