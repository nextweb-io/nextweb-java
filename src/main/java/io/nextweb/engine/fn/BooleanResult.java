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

public class BooleanResult implements BasicResult<Boolean>,
		AllInterceptor<BooleanResult> {

	private final Result<Boolean> result;
	private final ExceptionManager exceptionManager;
	private final Session session;

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	public BooleanResult and(final BooleanResult otherResult) {
		return new BooleanResult(exceptionManager, session,
				new AsyncResult<Boolean>() {

					@Override
					public void get(final Callback<Boolean> callback) {
						result.get(CallbackFactory.embeddedCallback(
								exceptionManager, callback,
								new Closure<Boolean>() {

									@Override
									public void apply(final Boolean o) {
										if (!o.booleanValue()) {
											callback.onSuccess(false);
											return;
										}

										otherResult.get(CallbackFactory
												.embeddedCallback(
														exceptionManager,
														callback,
														new Closure<Boolean>() {

															@Override
															public void apply(
																	final Boolean o) {
																callback.onSuccess(o);
															}
														}));

									}

								}));
					}
				});
	}

	public BooleanResult or(final BooleanResult otherResult) {
		return new BooleanResult(exceptionManager, session,
				new AsyncResult<Boolean>() {

					@Override
					public void get(final Callback<Boolean> callback) {
						result.get(CallbackFactory.embeddedCallback(
								exceptionManager, callback,
								new Closure<Boolean>() {

									@Override
									public void apply(final Boolean o) {
										if (o.booleanValue()) {
											callback.onSuccess(true);
											return;
										}

										otherResult.get(CallbackFactory
												.embeddedCallback(
														exceptionManager,
														callback,
														new Closure<Boolean>() {

															@Override
															public void apply(
																	final Boolean o) {
																callback.onSuccess(o);
															}
														}));

									}

								}));
					}
				});
	}

	@Override
	public Boolean get() {
		return result.get();
	}

	@Override
	public void get(final Closure<Boolean> callback) {
		result.get(callback);
	}

	@Override
	public void get(final Callback<Boolean> callback) {
		result.get(callback);
	}

	@Override
	public BooleanResult catchUnauthorized(final UnauthorizedListener listener) {
		exceptionManager.catchUnauthorized(listener);
		return this;
	}

	@Override
	public BooleanResult catchImpossible(final ImpossibleListener listener) {
		exceptionManager.catchImpossible(listener);
		return this;
	}

	@Override
	public BooleanResult catchExceptions(final ExceptionListener listener) {
		exceptionManager.catchExceptions(listener);
		return this;
	}

	@Override
	public BooleanResult catchUndefined(final UndefinedListener listener) {
		exceptionManager.catchUndefined(listener);
		return this;
	}

	@Override
	public AsyncResult<Boolean> getDecoratedResult() {
		return this.result;
	}

	public BooleanResult(final ExceptionManager eM, final Session session,
			final AsyncResult<Boolean> result) {
		super();
		this.exceptionManager = new ExceptionManager(eM);
		this.session = session;
		this.result = session.getEngine().getFactory()
				.createResult(this.exceptionManager, session, result);

	}
}
