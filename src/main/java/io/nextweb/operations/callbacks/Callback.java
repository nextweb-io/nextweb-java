package io.nextweb.operations.callbacks;

import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;

public interface Callback<ResultType> extends ExceptionListener,
		UnauthorizedListener, UndefinedListener {

	public void onSuccess(ResultType result);

	public boolean hasEagerFailureListener();

	public boolean hasEagerUndefinedListener();

	public boolean hasEagerUnauthorizedListener();

}
