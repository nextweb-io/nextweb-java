package io.nextweb.operations;

import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.operations.exceptions.UnauthorizedInterceptor;
import io.nextweb.operations.exceptions.UndefinedInterceptor;

public interface EntityListRequestOperations<EntityListType> extends
		ExceptionInterceptor<EntityListType>,
		UnauthorizedInterceptor<EntityListType>,
		UndefinedInterceptor<EntityListType> {

}
