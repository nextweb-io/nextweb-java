package io.nextweb.operations.callbacks;

import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;

public abstract class Callback<ResultType> implements
		UndefinedExceptionListener, AuthorizationExceptionListener,
		ExceptionListener {

	public abstract void onSuccess(ResultType result);

	protected abstract void onEmbeddedFailure(Object origin, Throwable t,
			Callback<ResultType> childCallback);

	protected abstract void onEmbeddedUnauthorized(Object origin,
			AuthorizationExceptionResult r, Callback<ResultType> childCallback);

	protected abstract void onEmbeddedUndefined(Object origin, String message,
			Callback<ResultType> childCallback);

	protected abstract void onHandedDownFailure(Object origin, Throwable t,
			Callback<ResultType> parentCallback);

	protected abstract void onHandedDownUnauthorized(Object origin,
			AuthorizationExceptionResult r, Callback<ResultType> parentCallback);

	protected abstract void onHandedDownUndefined(Object origin,
			String message, Callback<ResultType> childCallback);

	@Override
	public abstract void onFailure(Object origin, Throwable t);

	@Override
	public abstract void onUnauthorized(Object origin,
			AuthorizationExceptionResult r);

	@Override
	public abstract void onUndefined(Object origin, String message);

}
