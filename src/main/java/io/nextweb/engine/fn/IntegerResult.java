package io.nextweb.engine.fn;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.BasicResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.fn.callbacks.Callback;
import io.nextweb.fn.exceptions.AllInterceptor;
import io.nextweb.fn.exceptions.ExceptionListener;
import io.nextweb.fn.exceptions.ExceptionManager;
import io.nextweb.fn.exceptions.ImpossibleListener;
import io.nextweb.fn.exceptions.UnauthorizedListener;
import io.nextweb.fn.exceptions.UndefinedListener;
import io.nextweb.operations.callbacks.CallbackFactory;

public class IntegerResult implements BasicResult<Integer>,
		AllInterceptor<IntegerResult> {

	private final Result<Integer> result;
	private final ExceptionManager exceptionManager;
	private final Session session;

	public IntegerResult plus(final IntegerResult otherResult) {
		return new IntegerResult(exceptionManager, session,
				new AsyncResult<Integer>() {

					@Override
					public void get(final Callback<Integer> callback) {

						result.get(CallbackFactory.embeddedCallback(
								exceptionManager, callback,
								new Closure<Integer>() {

									@Override
									public void apply(
											final Integer thisResultValue) {

										otherResult.get(CallbackFactory
												.embeddedCallback(
														exceptionManager,
														callback,
														new Closure<Integer>() {

															@Override
															public void apply(
																	final Integer otherResultValue) {
																callback.onSuccess(thisResultValue
																		+ otherResultValue);
															}
														}));

									}
								}));
					}
				});
	}

	public IntegerResult minus(final IntegerResult otherResult) {
		return new IntegerResult(exceptionManager, session,
				new AsyncResult<Integer>() {

					@Override
					public void get(final Callback<Integer> callback) {

						result.get(CallbackFactory.embeddedCallback(
								exceptionManager, callback,
								new Closure<Integer>() {

									@Override
									public void apply(
											final Integer thisResultValue) {

										otherResult.get(CallbackFactory
												.embeddedCallback(
														exceptionManager,
														callback,
														new Closure<Integer>() {

															@Override
															public void apply(
																	final Integer otherResultValue) {
																callback.onSuccess(thisResultValue
																		- otherResultValue);
															}
														}));

									}
								}));
					}
				});
	}

	@Override
	public Integer get() {
		return result.get();
	}

	@Override
	public void get(final Closure<Integer> callback) {
		result.get(callback);
	}

	@Override
	public void get(final Callback<Integer> callback) {
		result.get(callback);
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	@Override
	public IntegerResult catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public IntegerResult catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public IntegerResult catchExceptions(final ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public IntegerResult catchImpossible(final ImpossibleListener listener) {
		exceptionManager.catchImpossible(listener);
		return this;
	}

	public IntegerResult(final ExceptionManager parentExceptionManager,
			final Session session, final AsyncResult<Integer> result) {
		super();
		this.exceptionManager = new ExceptionManager(parentExceptionManager);
		this.session = session;
		this.result = session.getEngine().getFactory()
				.createResult(this.exceptionManager, session, result);
	}

	@Override
	public AsyncResult<Integer> getDecoratedResult() {
		return this.result;
	}

}
