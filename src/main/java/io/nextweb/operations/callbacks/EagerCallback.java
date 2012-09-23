package io.nextweb.operations.callbacks;

import io.nextweb.operations.exceptions.AuthorizationExceptionResult;

public abstract class EagerCallback<ResultType> extends Callback<ResultType> {

	@Override
	protected void onEmbeddedFailure(Object origin, Throwable t,
			Callback<ResultType> childCallback) {
		onFailure(origin, t);
	}

	@Override
	protected void onEmbeddedUnauthorized(Object origin,
			AuthorizationExceptionResult r, Callback<ResultType> childCallback) {
		onUnauthorized(origin, r);
	}

	@Override
	protected void onEmbeddedUndefined(Object origin, String message,
			Callback<ResultType> childCallback) {
		onUndefined(origin, message);
	}

	@Override
	protected void onHandedDownFailure(Object origin, Throwable t,
			Callback<ResultType> parentCallback) {
		throw new IllegalStateException(
				"No exceptions should be handed down to eager callbacks.");
	}

	@Override
	protected void onHandedDownUnauthorized(Object origin,
			AuthorizationExceptionResult r, Callback<ResultType> parentCallback) {
		throw new IllegalStateException(
				"No exceptions should be handed down to eager callbacks.");
	}

	@Override
	protected void onHandedDownUndefined(Object origin, String message,
			Callback<ResultType> childCallback) {
		throw new IllegalStateException(
				"No exceptions should be handed down to eager callbacks.");
	}

	@Override
	public final void onFailure(Object origin, Throwable t) {

	}

	@Override
	public final void onUnauthorized(Object origin,
			AuthorizationExceptionResult r) {

	}

	@Override
	public final void onUndefined(Object origin, String message) {

	}

	/**
	 * Overwrite and return true to catch failures.
	 * 
	 * @param origin
	 * @param t
	 * @return
	 */
	public boolean catchFailure(Object origin, Throwable t) {
		return false;
	}

	public boolean catchUnauthorized(Object origin,
			AuthorizationExceptionResult r) {
		return false;
	}

	public boolean catchUndefined(Object origin, String message) {
		return false;
	}

}
