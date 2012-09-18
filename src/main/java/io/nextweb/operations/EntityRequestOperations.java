package io.nextweb.operations;

import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.operations.exceptions.AuthorizationExceptionInterceptor;

public interface EntityRequestOperations extends ExceptionInterceptor,
		AuthorizationExceptionInterceptor {

}
