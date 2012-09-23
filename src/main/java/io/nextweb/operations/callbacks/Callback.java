package io.nextweb.operations.callbacks;

import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;

public interface Callback<ResultType> extends ExceptionListener,
		AuthorizationExceptionListener, UndefinedExceptionListener {

	public void onSuccess(ResultType result);

	public boolean hasEagerFailureListener();

	public boolean hasEagerUndefinedListener();

	public boolean hasEagerUnauthorizedListener();

}
