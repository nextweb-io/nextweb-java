package io.nextweb.fn;

import io.nextweb.operations.exceptions.AuthorizationExceptionResult;

public interface RequestCallback<ResultType> {

	public abstract void onUnauthorized(Object origin,
			AuthorizationExceptionResult r);

	public abstract void onUndefined(Object origin, String message);

	public abstract void onFailure(Object origin, Throwable t);

	public abstract void onSuccess(ResultType result);

}