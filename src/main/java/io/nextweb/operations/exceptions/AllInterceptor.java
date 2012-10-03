package io.nextweb.operations.exceptions;

import io.nextweb.fn.ExceptionInterceptor;

/**
 * <p>
 * Interface marks capability to define interceptors for all basic exception
 * types.
 * </p>
 * 
 * @author Max Rohde
 * 
 * @param <EntityType>
 */
public interface AllInterceptor<EntityType> extends
		ExceptionInterceptor<EntityType>, UnauthorizedInterceptor<EntityType>,
		UndefinedInterceptor<EntityType>, ImpossibleInterceptor<EntityType> {

}
