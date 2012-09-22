package io.nextweb.fn;

import io.nextweb.Nextweb;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;

public abstract class RequestCallbackImpl<ResultType> implements
		RequestCallback<ResultType> {

	private final ExceptionManager exceptionManager;
	private final RequestCallback<ResultType> nestedIn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nextweb.fn.RequestCallback#onUnauthorized(java.lang.Object,
	 * io.nextweb.operations.exceptions.AuthorizationExceptionResult)
	 */
	@Override
	public void onUnauthorized(Object origin, AuthorizationExceptionResult r) {
		if (exceptionManager.canCatchAuthorizationExceptions()) {
			exceptionManager.onUnauthorized(origin, r);
			return;
		}

		nestedIn.onUnauthorized(origin, r);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nextweb.fn.RequestCallback#onUndefined(java.lang.Object,
	 * java.lang.String)
	 */
	@Override
	public void onUndefined(Object origin, String message) {
		if (exceptionManager.canCatchUndefinedExceptions()) {
			exceptionManager.onUndefined(origin, message);
			return;
		}

		nestedIn.onUndefined(origin, message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nextweb.fn.RequestCallback#onFailure(java.lang.Object,
	 * java.lang.Throwable)
	 */
	@Override
	public void onFailure(Object origin, Throwable t) {
		if (exceptionManager.canCatchExceptions()) {
			exceptionManager.onFailure(origin, t);
			return;
		}

		nestedIn.onFailure(origin, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.nextweb.fn.RequestCallback#onSuccess(ResultType)
	 */
	@Override
	public abstract void onSuccess(ResultType result);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <Callback extends RequestCallbackImpl<?>> Callback doNothing() {
		return (Callback) new RequestCallbackImpl(Nextweb.getEngine()
				.getExceptionManager(), null) {

			@Override
			public void onSuccess(Object result) {

			}

		};
	}

	public RequestCallbackImpl(ExceptionManager exceptionManager,
			RequestCallback<ResultType> nestedIn) {
		super();
		this.exceptionManager = exceptionManager;
		if (nestedIn != null) {
			this.nestedIn = nestedIn;
		} else {
			this.nestedIn = new RequestCallback<ResultType>() {

				@Override
				public void onSuccess(ResultType result) {
					throw new RuntimeException("Should not be called.");
				}

				@Override
				public void onUnauthorized(Object origin,
						AuthorizationExceptionResult r) {
					Nextweb.getEngine().getExceptionManager()
							.onUnauthorized(origin, r);
				}

				@Override
				public void onUndefined(Object origin, String message) {
					Nextweb.getEngine().getExceptionManager()
							.onUndefined(origin, message);
				}

				@Override
				public void onFailure(Object origin, Throwable t) {
					Nextweb.getEngine().getExceptionManager()
							.onFailure(origin, t);

				}
			};
		}
	}

}
