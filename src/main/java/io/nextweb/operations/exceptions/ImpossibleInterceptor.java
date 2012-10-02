package io.nextweb.operations.exceptions;

public interface ImpossibleInterceptor<ReturnType> {

	public ReturnType catchImpossible(ImpossibleListener listener);

}
