package io.nextweb.operations.callbacks;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
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
	public final void onFailure(ExceptionResult r) {
		if (hasEagerFailureListener) {
			this.exceptionListener.onFailure(r);
			return;
		}

		if (exceptionManager.canCatchExceptions()) {
			exceptionManager.onFailure(r);
			return;
		}

		if (session != null
				&& session.getExceptionManager().canCatchExceptions()) {
			session.getExceptionManager().onFailure(r);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onFailure(r);

	}

	@Override
	public final void onUnauthorized(UnauthorizedResult r) {
		if (hasEagerUnauthorizedListener) {
			this.authExceptionListener.onUnauthorized(r);
			return;
		}

		if (hasEagerFailureListener) {
			this.exceptionListener.onFailure(Fn.exception(r.origin(),
					new Exception("Unauthorized: " + r.getMessage())));
			return;
		}

		if (exceptionManager.canCatchAuthorizationExceptions()) {
			exceptionManager.onUnauthorized(r);
			return;
		}

		if (session != null
				&& session.getExceptionManager()
						.canCatchAuthorizationExceptions()) {
			session.getExceptionManager().onUnauthorized(r);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onUnauthorized(r);
	}

	@Override
	public final void onUndefined(UndefinedResult r) {
		if (hasEagerUndefinedListener) {
			this.undefinedExceptionListenr.onUndefined(r);
			return;
		}

		if (hasEagerFailureListener) {
			this.exceptionListener.onFailure(Fn.exception(r.origin(),
					new Exception("Undefined: " + r.message())));
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
