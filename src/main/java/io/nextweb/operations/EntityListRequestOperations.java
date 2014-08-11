package io.nextweb.operations;

import io.nextweb.promise.exceptions.ExceptionInterceptor;
import io.nextweb.promise.exceptions.UnauthorizedInterceptor;
import io.nextweb.promise.exceptions.UndefinedInterceptor;

public interface EntityListRequestOperations<EntityListType> extends
		ExceptionInterceptor<EntityListType>,
		UnauthorizedInterceptor<EntityListType>,
		UndefinedInterceptor<EntityListType> {

}
