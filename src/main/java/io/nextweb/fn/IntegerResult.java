package io.nextweb.fn;

import io.nextweb.Session;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ExceptionManager;

public class IntegerResult implements Result<Integer> {

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
																	Integer otherResultValue) {
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
																	Integer otherResultValue) {
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
	public void get(Closure<Integer> callback) {
		result.get(callback);
	}

	@Override
	public void get(Callback<Integer> callback) {
		result.get(callback);
	}

	public IntegerResult(ExceptionManager exceptionManager, Session session,
			AsyncResult<Integer> result) {
		super();
		this.exceptionManager = exceptionManager;
		this.session = session;
		this.result = session.getEngine().createResult(exceptionManager,
				session, result);
	}

}
