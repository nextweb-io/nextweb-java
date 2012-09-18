package io.nextweb.operations;

import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.operations.exceptions.AuthorizationExceptionInterceptor;
import io.nextweb.operations.exceptions.UndefinedExceptionInterceptor;

public interface EntityRequestOperations extends ExceptionInterceptor,
		AuthorizationExceptionInterceptor, UndefinedExceptionInterceptor {

}
