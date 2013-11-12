package io.nextweb.operations.callbacks;

import io.nextweb.Session;
import io.nextweb.engine.NextwebGlobal;
import io.nextweb.fn.callbacks.Callback;
import io.nextweb.fn.exceptions.ExceptionManager;
import io.nextweb.fn.exceptions.ExceptionResult;
import io.nextweb.fn.exceptions.ImpossibleResult;
import io.nextweb.fn.exceptions.UnauthorizedResult;
import io.nextweb.fn.exceptions.UndefinedResult;

public abstract class LazyCallback<ResultType> implements Callback<ResultType> {

	private final ExceptionManager exceptionManager;
	private final Session session;

	public LazyCallback(final ExceptionManager exceptionManager,
			final Session session) {
		super();
		this.exceptionManager = exceptionManager;
		this.session = session;
	}

	@Override
	public void onFailure(final ExceptionResult r) {

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
	public void onUnauthorized(final UnauthorizedResult r) {

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
	public void onImpossible(final ImpossibleResult ir) {

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
	public void onUndefined(final UndefinedResult r) {

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
	public boolean hasEagerImpossibleListener() {
		return false;
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
