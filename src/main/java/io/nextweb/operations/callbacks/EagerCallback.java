package io.nextweb.operations.callbacks;

import io.nextweb.Session;
import io.nextweb.engine.NextwebGlobal;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.ImpossibleListener;
import io.nextweb.operations.exceptions.ImpossibleResult;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;

public abstract class EagerCallback<ResultType> implements Callback<ResultType> {

	private boolean hasEagerFailureListener;
	private boolean hasEagerUndefinedListener;
	private boolean hasEagerUnauthorizedListener;
	private boolean hasEagerImpossibleListener;
	private ExceptionListener exceptionListener;
	private final Session session;
	private final ExceptionManager exceptionManager;
	private UnauthorizedListener authExceptionListener;
	private UndefinedListener undefinedExceptionListenr;
	private ImpossibleListener impossibleListener;

	public EagerCallback<ResultType> catchExceptions(
			ExceptionListener exceptionListener) {
		hasEagerFailureListener = true;
		this.exceptionListener = exceptionListener;
		return this;
	}

	public EagerCallback<ResultType> catchUnauthorized(
			UnauthorizedListener exceptionListener) {
		hasEagerUnauthorizedListener = true;
		this.authExceptionListener = exceptionListener;
		return this;
	}

	public EagerCallback<ResultType> catchUndefined(
			UndefinedListener listener) {
		hasEagerUndefinedListener = true;
		this.undefinedExceptionListenr = listener;
		return this;
	}

	public EagerCallback<ResultType> catchImpossible(ImpossibleListener listener) {
		hasEagerImpossibleListener = true;
		this.impossibleListener = listener;
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

		NextwebGlobal.getEngine().getExceptionManager().onFailure(r);

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

		NextwebGlobal.getEngine().getExceptionManager().onUnauthorized(r);
	}

	@Override
	public void onImpossible(ImpossibleResult ir) {
		if (hasEagerImpossibleListener) {
			this.impossibleListener.onImpossible(ir);
			return;
		}

		if (hasEagerFailureListener) {
			this.exceptionListener.onFailure(Fn.exception(ir.origin(),
					new Exception("Operation impossible: [" + ir.message()
							+ "]")));
		}

		if (exceptionManager.canCatchImpossibe()) {
			exceptionManager.onImpossible(ir);
			return;
		}

		if (session != null
				&& session.getExceptionManager().canCatchImpossibe()) {
			session.getExceptionManager().onImpossible(ir);
			return;
		}

		NextwebGlobal.getEngine().getExceptionManager().onImpossible(ir);

	}

	@Override
	public boolean hasEagerImpossibleListener() {
		return hasEagerImpossibleListener || hasEagerFailureListener;
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

		NextwebGlobal.getEngine().getExceptionManager().onUndefined(r);
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
