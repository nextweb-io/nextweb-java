package io.nextweb.operations;

import io.nextweb.Entity;
import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.operations.exceptions.AuthorizationExceptionInterceptor;
import io.nextweb.operations.exceptions.UndefinedExceptionInterceptor;

public interface EntityRequestOperations<EntityType extends Entity> extends
		ExceptionInterceptor<EntityType>, AuthorizationExceptionInterceptor,
		UndefinedExceptionInterceptor {

}
