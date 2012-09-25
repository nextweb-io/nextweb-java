package io.nextweb.operations.entity;

import io.nextweb.Entity;
import io.nextweb.fn.ExceptionInterceptor;
import io.nextweb.operations.exceptions.UnauthorizedInterceptor;
import io.nextweb.operations.exceptions.UndefinedInterceptor;

public interface EntityRequestOperations<EntityType extends Entity> extends
		ExceptionInterceptor<EntityType>,
		UnauthorizedInterceptor<EntityType>,
		UndefinedInterceptor<EntityType> {

}
