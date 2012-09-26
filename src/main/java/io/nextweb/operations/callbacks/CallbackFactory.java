package io.nextweb.operations.callbacks;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.LinkListQuery;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.operations.exceptions.ExceptionManager;

public class CallbackFactory {

	@SuppressWarnings("unchecked")
	public static <ResultType> Callback<ResultType> embeddedCallback(
			final ExceptionManager exceptionManager,
			Callback<ResultType> embeddedIn) {
		return new EmbeddedCallback<ResultType>((Callback<Object>) embeddedIn,
				exceptionManager);
	}

	@SuppressWarnings("unchecked")
	public static <ResultType> Callback<ResultType> embeddedCallback(
			final ExceptionManager exceptionManager, Callback<?> embeddedIn,
			final Closure<ResultType> p_onSuccess) {
		return new EmbeddedCallback<ResultType>((Callback<Object>) embeddedIn,
				exceptionManager) {

			@Override
			public void onSuccess(ResultType result) {
				p_onSuccess.apply(result);
			}

		};
	}

	public static <ResultType> Callback<ResultType> lazyCallback(
			final Entity entity, final Closure<ResultType> closure) {
		return lazyCallback(entity.getSession(), entity.getExceptionManager(),
				closure);
	}

	public static <ResultType> Callback<ResultType> lazyCallback(
			final LinkListQuery entity, final Closure<ResultType> closure) {
		return lazyCallback(entity.getSession(), entity.getExceptionManager(),
				closure);
	}

	public static <ResultType> Callback<ResultType> lazyCallback(
			EntityList entity, Closure<ResultType> closure) {

		return lazyCallback(entity.getSession(), entity.getExceptionManager(),
				closure);

	}

	public static <ResultType> Callback<ResultType> lazyCallback(
			final Session session, final ExceptionManager exceptionManager,
			final Closure<ResultType> closure) {

		return new LazyCallback<ResultType>(exceptionManager, session) {

			@Override
			public void onSuccess(ResultType result) {
				closure.apply(result);
			}
		};

	}

	public static <ResultType> EagerCallback<ResultType> eagerCallback(
			final Session session, final ExceptionManager exceptionManager,
			final Closure<ResultType> closure) {
		return new EagerCallback<ResultType>(session, exceptionManager) {

			@Override
			public void onSuccess(ResultType result) {
				closure.apply(result);
			}
		};
	}

}
