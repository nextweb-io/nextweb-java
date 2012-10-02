package io.nextweb.js.operations;

import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

public interface JsExceptionListeners {
	public JsExceptionManager catchExceptions(final JsClosure exceptionListener);

	public JsExceptionManager catchUndefined(final JsClosure undefinedListener);

	public JsExceptionManager catchUnauthorized(
			final JsClosure unauthorizedListener);

	public JsExceptionManager catchImpossible(final JsClosure impossibleListener);
}
