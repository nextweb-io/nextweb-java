package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.callbacks.EagerCallback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.async.joiner.CallbackLatch;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenShutdown;

import com.ononedb.nextweb.internal.OnedbFactory;

public class OnedbSession implements Session {

	private final OnedbNextwebEngine engine;
	private final OneClient client;
	private final ExceptionManager exceptionManager;

	public OneClient getClient() {
		return client;
	}

	public OnedbFactory getFactory() {
		return getOnedbEngine().getFactory();
	}

	public OnedbNextwebEngine getOnedbEngine() {
		return this.engine;
	}

	@Override
	public NextwebEngine getEngine() {
		return getOnedbEngine();
	}

	public OnedbSession(OnedbNextwebEngine engine, OneClient client) {
		super();
		this.engine = engine;
		this.client = client;
		this.exceptionManager = engine.getFactory()
				.createExceptionManager(null);
	}

	@Override
	public Result<SuccessFail> close() {

		Result<SuccessFail> closeResult = this.engine.createResult(
				exceptionManager, this, new AsyncResult<SuccessFail>() {

					@Override
					public void get(final Callback<SuccessFail> callback) {

						client.one().shutdown(client).and(new WhenShutdown() {

							@Override
							public void thenDo() {
								callback.onSuccess(SuccessFail.success());
							}

							@Override
							public void onFailure(Throwable t) {
								callback.onSuccess(SuccessFail.fail(t));
							}

						});

					}

				});

		closeResult.get(new Closure<SuccessFail>() {

			@Override
			public void apply(SuccessFail o) {
				// nothing
			}

		});

		return closeResult;
	}

	@Override
	public Link node(String uri) {
		return engine.getFactory().createLink(this, null, uri); // _NO_ parent
																// exception
																// Manager
	}

	@Override
	public void getAll(Result<?>... results) {

		Result<SuccessFail> callback = getAll(true, results);

		SuccessFail result = callback.get();

		if (result.isFail()) {
			throw new RuntimeException(result.getException());
		}
	}

	@Override
	public Result<SuccessFail> getAll(final boolean asynchronous,
			final Result<?>... results) {
		Result<SuccessFail> getAllResult = engine.createResult(
				exceptionManager, this, new AsyncResult<SuccessFail>() {

					@SuppressWarnings({ "unchecked" })
					@Override
					public void get(final Callback<SuccessFail> callback) {

						final CallbackLatch latch = new CallbackLatch(
								results.length) {

							@Override
							public void onFailed(Throwable arg0) {
								callback.onSuccess(SuccessFail.fail(arg0));
							}

							@Override
							public void onCompleted() {
								callback.onSuccess(SuccessFail.success());
							}
						};

						Result<Object>[] resultObj = (Result<Object>[]) results;

						for (Result<Object> result : resultObj) {
							EagerCallback<Object> eagerCallback = CallbackFactory
									.eagerCallback(OnedbSession.this,
											exceptionManager,
											new Closure<Object>() {

												@Override
												public void apply(Object o) {
													latch.registerSuccess();
												}

											}).catchFailures(
											new ExceptionListener() {

												@Override
												public void onFailure(
														Object origin,
														Throwable t) {
													latch.registerFail(t);
												}
											});
							result.get(eagerCallback);

						}

					}
				});

		return getAllResult;
	}

	@Override
	public <PluginType extends Plugin<Session>> PluginType plugin(
			PluginFactory<Session, PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

}
