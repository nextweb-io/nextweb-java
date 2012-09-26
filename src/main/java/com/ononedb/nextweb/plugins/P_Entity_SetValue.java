package com.ononedb.nextweb.plugins;

import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Fn;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.plugins.core.Plugin_Entity_SetValue;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenResponseFromServerReceived;
import one.core.dsl.callbacks.results.WithImpossibleContext;
import one.core.dsl.callbacks.results.WithOperationResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;
import one.core.nodes.OneValue;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.OnedbQuery;
import com.ononedb.nextweb.common.H;

public class P_Entity_SetValue implements Plugin_Entity_SetValue<OnedbEntity> {

	OnedbEntity entity;

	@Override
	public Query setValueSafe(final Object newValue) {

		AsyncResult<Node> setValueResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(Node o) {
								final CoreDsl dsl = H.dsl(entity);

								final OneTypedReference<?> node = dsl
										.reference(o.getUri());

								final Object dereferenced = dsl.dereference(
										node).in(H.session(entity).getClient());
								final Object replacement;
								if (dereferenced instanceof OneValue<?>) {
									replacement = dsl.newNode(newValue).at(
											o.getUri());
								} else {
									replacement = newValue;
								}

								dsl.replaceSafe(node)
										.with(replacement)
										.in(H.client(entity))
										.and(new WhenResponseFromServerReceived<Object>() {

											@Override
											public void thenDo(
													WithOperationResult<Object> or) {
												callback.onSuccess(H
														.factory(entity)
														.createNode(
																entity.getOnedbSession(),
																entity.getExceptionManager(),
																dsl.reference(node
																		.getId())));
											}

											@Override
											public void onUnauthorized(
													WithUnauthorizedContext context) {
												callback.onUnauthorized(H
														.fromUnauthorizedContext(
																this, context));
											}

											@Override
											public void onImpossible(
													WithImpossibleContext context) {
												callback.onImpossible(H
														.fromImpossibleContext(
																this, context));
											}

											@Override
											public void onFailure(Throwable t) {
												callback.onFailure(Fn
														.exception(this, t));
											}

										});

							}
						}));

			}
		};

		OnedbQuery createQuery = H.factory(entity).createQuery(
				entity.getOnedbSession(), entity.getExceptionManager(),
				setValueResult);

		createQuery.get(new Closure<Node>() {

			@Override
			public void apply(Node o) {
				// nothing
			}
		});

		return createQuery;
	}

	@Override
	public Query setValue(final Object newValue) {

		AsyncResult<Node> setValueResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(final Node o) {
								H.engine(entity).runSafe(H.session(entity),
										new Runnable() {

											@Override
											public void run() {

												CoreDsl dsl = H.dsl(entity);

												OneTypedReference<?> node = dsl
														.reference(o.getUri());
												Object dereferenced = dsl
														.dereference(node)
														.in(H.session(entity)
																.getClient());

												if (dereferenced instanceof OneValue<?>) {

													OneValue<?> newValueObject = dsl
															.newNode(newValue)
															.at(o.getUri());

													dsl.replace(node)
															.with(newValueObject)
															.in(H.client(entity));
													callback.onSuccess(o);
													return;
												}

												dsl.replace(node)
														.with(newValue)
														.in(H.client(entity));
												callback.onSuccess(o);
											}
										});
							}
						}));

			}
		};
		OnedbQuery query = H.factory(entity).createQuery(
				entity.getOnedbSession(), entity.getExceptionManager(),
				setValueResult);

		query.get(new Closure<Node>() {

			@Override
			public void apply(Node o) {
				// nothing
			}
		});

		return query;

	}

	@Override
	public void injectObject(OnedbEntity obj) {
		this.entity = obj;
	}

}
