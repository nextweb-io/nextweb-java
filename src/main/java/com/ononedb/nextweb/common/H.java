package com.ononedb.nextweb.common;

import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;

import com.ononedb.nextweb.OnedbObject;
import com.ononedb.nextweb.internal.OnedbFactory;

/**
 * Helper methods.
 * 
 * @author mroh004
 * 
 */
public class H {

	public static CoreDsl dsl(OnedbObject fromObj) {
		return client(fromObj).one();
	}

	public static OneClient client(OnedbObject fromObj) {
		return fromObj.getOnedbSession().getClient();
	}

	public static OnedbFactory factory(OnedbObject fromObj) {
		return fromObj.getOnedbSession().getFactory();
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
