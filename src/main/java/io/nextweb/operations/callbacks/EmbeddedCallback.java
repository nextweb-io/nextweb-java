package io.nextweb.operations.callbacks;

import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;

public class EmbeddedCallback<ResultType> implements Callback<ResultType> {

	private final Callback<Object> embeddedIn;
	private final ExceptionManager exceptionManager;

	@Override
	public void onUndefined(Object origin, String message) {

		if (hasEagerUndefinedListener()) {
			embeddedIn.onUndefined(origin, message);
			return;
		}

		if (exceptionManager.canCatchUndefinedExceptions()) {
			exceptionManager.onUndefined(origin, message);
			return;
		}

		embeddedIn.onUndefined(origin, message);

	}

	@Override
	public void onFailure(Object origin, Throwable t) {
		if (hasEagerFailureListener()) {
			embeddedIn.onFailure(origin, t);
			return;
		}

		if (exceptionManager.canCatchExceptions()) {
			exceptionManager.onFailure(origin, t);
			return;
		}

		embeddedIn.onFailure(origin, t);
	}

	@Override
	public void onUnauthorized(Object origin, AuthorizationExceptionResult r) {
		if (hasEagerUnauthorizedListener()) {
			embeddedIn.onUnauthorized(origin, r);
			return;
		}

		if (exceptionManager.canCatchAuthorizationExceptions()) {
			exceptionManager.onUnauthorized(origin, r);
			return;
		}

		embeddedIn.onUnauthorized(origin, r);
	}

	@Override
	public void onSuccess(ResultType result) {
		embeddedIn.onSuccess(result);
	}

	@Override
	public boolean hasEagerFailureListener() {
		return embeddedIn.hasEagerFailureListener();
	}

	@Override
	public boolean hasEagerUndefinedListener() {
		return embeddedIn.hasEagerUndefinedListener();
	}

	@Override
	public boolean hasEagerUnauthorizedListener() {
		return embeddedIn.hasEagerUnauthorizedListener();
	}

	public EmbeddedCallback(Callback<Object> embeddedIn,
			ExceptionManager exceptionManager) {
		super();
		this.embeddedIn = embeddedIn;
		this.exceptionManager = exceptionManager;
	}

}
