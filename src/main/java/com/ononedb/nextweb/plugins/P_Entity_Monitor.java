package com.ononedb.nextweb.plugins;

import io.nextweb.Node;
import io.nextweb.common.Interval;
import io.nextweb.common.Monitor;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.plugins.core.Plugin_Entity_Monitor;
import one.core.domain.OneMonitor;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenNodeChanged;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithNodeChangedContext;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.common.H;

public class P_Entity_Monitor implements Plugin_Entity_Monitor<OnedbEntity> {

	OnedbEntity entity;

	@Override
	public Result<Monitor> monitor(final Interval interval,
			final Closure<Node> whenChanged) {
		final AsyncResult<Monitor> monitorAsycResult = new AsyncResult<Monitor>() {

			@Override
			public void get(final Callback<Monitor> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(final Node o) {
								final CoreDsl dsl = H.dsl(entity);
								one.core.dsl.grammars.MonitorNodeGrammar.Interval selectedInterval = null;
								for (final one.core.dsl.grammars.MonitorNodeGrammar.Interval onedbInterval : one.core.dsl.grammars.MonitorNodeGrammar.Interval
										.values()) {
									if (onedbInterval.getMilliseconds() == interval
											.getMilliseconds()) {
										selectedInterval = onedbInterval;
									}
								}

								assert selectedInterval != null;

								final OneMonitor monitor = dsl
										.monitor(dsl.reference(o.getUri()))
										.inInterval(selectedInterval)
										.in(H.client(entity))
										.and(new WhenNodeChanged() {

											@Override
											public void thenDo(
													final WithNodeChangedContext context) {
												assert context.changedNode()
														.getId()
														.equals(o.getUri());

												whenChanged.apply(o);
											}

											@Override
											public void onFailure(
													final Throwable t) {
												entity.getExceptionManager()
														.onFailure(
																Fn.exception(
																		this, t));
											}

										});

								callback.onSuccess(new Monitor() {

									@Override
									public Result<Success> stop() {

										final AsyncResult<Success> stopResult = new AsyncResult<Success>() {

											@Override
											public void get(
													final Callback<Success> callback) {
												monitor.stop(new WhenShutdown() {

													@Override
													public void thenDo() {
														callback.onSuccess(Success.INSTANCE);
													}

													@Override
													public void onFailure(
															final Throwable t) {
														callback.onFailure(Fn
																.exception(
																		this, t));
													}

												});
											}
										};

										return H.engine(entity)
												.createResult(
														entity.getExceptionManager(),
														entity.getSession(),
														stopResult);
									}
								});
							}
						}));

			}
		};
		final Result<Monitor> monitorResult = H.engine(entity).createResult(
				entity.getExceptionManager(), entity.getSession(),
				monitorAsycResult);

		monitorResult.get(new Closure<Monitor>() {

			@Override
			public void apply(final Monitor o) {
				// nothing
			}
		});

		return monitorResult;
	}

	@Override
	public void injectObject(final OnedbEntity obj) {
		this.entity = obj;
	}

}
