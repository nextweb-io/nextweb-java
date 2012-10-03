package io.nextweb.fn;

import io.nextweb.Session;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ExceptionManager;

public class BooleanResult implements Result<Boolean> {

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

	public BooleanResult(final ExceptionManager eM, final Session session,
			final AsyncResult<Boolean> result) {
		super();
		this.exceptionManager = new ExceptionManager(eM);
		this.session = session;
		this.result = session.getEngine().createResult(eM, session, result);

	}
}
