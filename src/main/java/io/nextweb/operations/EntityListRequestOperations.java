package io.nextweb.operations;

import io.nextweb.fn.exceptions.ExceptionInterceptor;
import io.nextweb.fn.exceptions.UnauthorizedInterceptor;
import io.nextweb.fn.exceptions.UndefinedInterceptor;

public interface EntityListRequestOperations<EntityListType> extends
		ExceptionInterceptor<EntityListType>,
		UnauthorizedInterceptor<EntityListType>,
		UndefinedInterceptor<EntityListType> {

}
