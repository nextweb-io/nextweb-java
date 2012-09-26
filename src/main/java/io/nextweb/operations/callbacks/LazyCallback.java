package io.nextweb.operations.callbacks;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.ImpossibleResult;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedResult;

public abstract class LazyCallback<ResultType> implements Callback<ResultType> {

	private final ExceptionManager exceptionManager;
	private final Session session;

	public LazyCallback(ExceptionManager exceptionManager, Session session) {
		super();
		this.exceptionManager = exceptionManager;
		this.session = session;
	}

	@Override
	public void onFailure(ExceptionResult r) {

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
	public void onUnauthorized(UnauthorizedResult r) {

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
	public void onImpossible(ImpossibleResult ir) {

		if (exceptionManager.canCatchImpossibe()) {
			exceptionManager.onImpossible(ir);
			return;
		}

		if (session != null
				&& session.getExceptionManager().canCatchImpossibe()) {
			session.getExceptionManager().onImpossible(ir);
			return;
		}

		Nextweb.getEngine().getExceptionManager().onImpossible(ir);
	}

	@Override
	public void onUndefined(UndefinedResult r) {

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
