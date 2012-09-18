package com.ononedb.nextweb.common;

import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;

public class H {

	public static CoreDsl dsl(OnedbObject fromObj) {
		return fromObj.getOnedbSession().getClient().one();
	}

	public static AuthorizationExceptionResult fromUnauthorizedContext(
			final WithUnauthorizedContext context) {
		return new AuthorizationExceptionResult() {

			@Override
			public Object getType() {

				return context.cause();
			}

			@Override
			public String getMessage() {

				return context.message();
			}
		};
	}

}
