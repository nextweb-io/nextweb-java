package com.ononedb.nextweb.plugins.impl;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import one.core.dsl.CoreDsl;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.plugins.Plugin_Entity_Remove;

public class P_Entity_Remove implements Plugin_Entity_Remove<OnedbEntity> {

	OnedbEntity entity;

	@Override
	public void injectObject(OnedbEntity obj) {
		this.entity = obj;
	}

	@Override
	public Result<Success> remove(final Entity whichEntity) {

		final AsyncResult<Success> removeResult = new AsyncResult<Success>() {

			@Override
			public void get(final Callback<Success> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(final Node o) {

								if (whichEntity instanceof Link) {
									performRemove(o,
											((Link) whichEntity).getUri());
									return;
								}

								whichEntity.get(CallbackFactory
										.embeddedCallback(
												entity.getExceptionManager(),
												callback, new Closure<Node>() {

													@Override
													public void apply(
															Node whichNode) {
														performRemove(
																o,
																whichNode
																		.getUri());
													}
												}));

							}

							private void performRemove(Node o,
									String whichNodeUri) {

								try {
									final CoreDsl dsl = H.dsl(entity);

									OneTypedReference<?> fromNode = dsl
											.reference(o.getUri());

									OneTypedReference<?> whichNode = dsl
											.reference(whichNodeUri);

									dsl.remove(whichNode).fromNode(fromNode)
											.in(H.client(entity));

								} catch (Exception e) {
									callback.onFailure(Fn.exception(this, e));
									return;
								}

								callback.onSuccess(Success.INSTANCE);

							}
						}));

			}
		};

		Result<Success> result = H
				.session(entity)
				.getEngine()
				.createResult(entity.getExceptionManager(), H.session(entity),
						removeResult);

		result.get(new Closure<Success>() {

			@Override
			public void apply(Success o) {

				// do nothing
			}
		});

		return result;
	}
}
