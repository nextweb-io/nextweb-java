package io.nextweb.operations.callbacks;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.ExceptionManager;

public abstract class LazyCallback<ResultType> implements Callback<ResultType> {

	private final ExceptionManager exceptionManager;
	private final Session session;

	public LazyCallback(ExceptionManager exceptionManager, Session session) {
		super();
		this.exceptionManager = exceptionManager;
		this.session = session;
	}

	@Override
	public void onFailure(Object origin, Throwable t) {
		if (session != null
				&& session.getExceptionManager().canCatchExceptions()) {
			session.getExceptionManager().onFailure(origin, t);
			return;
		}

		if (exceptionManager.canCatchExceptions()) {
			exceptionManager.onFailure(origin, t);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onFailure(origin, t);
	}

	@Override
	public void onUnauthorized(Object origin, UnauthorizedResult r) {
		if (session != null
				&& session.getExceptionManager()
						.canCatchAuthorizationExceptions()) {
			session.getExceptionManager().onUnauthorized(origin, r);
			return;
		}

		if (exceptionManager.canCatchAuthorizationExceptions()) {
			exceptionManager.onUnauthorized(origin, r);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onUnauthorized(origin, r);
	}

	@Override
	public void onUndefined(Object origin, String message) {
		if (session != null
				&& session.getExceptionManager().canCatchUndefinedExceptions()) {
			session.getExceptionManager().onUndefined(origin, message);
			return;
		}

		if (exceptionManager.canCatchUndefinedExceptions()) {
			exceptionManager.onUndefined(origin, message);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onUndefined(origin, message);
	}

	@Override
	public boolean hasEagerFailureListener() {
		return false;
	}

	@Override
	public boolean hasEagerUndefinedListener() {
		return false;
	}

	@Override
	public boolean hasEagerUnauthorizedListener() {
		return false;
	}

}
