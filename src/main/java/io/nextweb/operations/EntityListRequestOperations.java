package io.nextweb.operations;

import io.nextweb.EntityList;
import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.operations.exceptions.AuthorizationExceptionInterceptor;
import io.nextweb.operations.exceptions.UndefinedExceptionInterceptor;

public interface EntityListRequestOperations<EntityListType extends EntityList<?>>
		extends ExceptionInterceptor<EntityListType>,
		AuthorizationExceptionInterceptor<EntityListType>,
		UndefinedExceptionInterceptor<EntityListType> {

}
