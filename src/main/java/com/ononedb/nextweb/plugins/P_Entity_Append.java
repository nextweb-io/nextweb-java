package com.ononedb.nextweb.plugins;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.plugins.core.Plugin_Entity_Append;
import one.core.dsl.CoreDsl;
import one.core.nodes.OneTypedReference;
import one.core.nodes.OneValue;
import one.utils.OneUtilsStrings;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.common.H;

public class P_Entity_Append implements Plugin_Entity_Append<OnedbEntity> {

	OnedbEntity entity;

	@Override
	public Query append(final Object value) {

		AsyncResult<Node> appendResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(final Node node) {
								H.client(entity).runSafe(new Runnable() {

									@Override
									public void run() {
										CoreDsl dsl = H.dsl(entity);

										String address = value.toString();

										if (address.length() > 8) {
											address = address.substring(0, 7);
										}

										String cleanedString = "";
										for (int i = 0; i <= address.length() - 1; i++) {
											if (!OneUtilsStrings
													.isSimpleCharacter(address
															.charAt(i))) {
												cleanedString = cleanedString
														+ "_";
											} else {
												cleanedString = cleanedString
														+ address.charAt(i);
											}
										}

										cleanedString = cleanedString
												+ (dsl.selectFrom(
														dsl.reference(node
																.getUri()))
														.allChildrenFast()
														.in(H.client(entity))
														.size() + 1);

										append(value, cleanedString)
												.get(CallbackFactory.embeddedCallback(
														entity.getExceptionManager(),
														callback,
														new Closure<Node>() {

															@Override
															public void apply(
																	Node o) {
																callback.onSuccess(o);
															}

														}));
									}
								});
							}

						}));

			}

		};

		return H.factory(entity).createQuery(H.session(entity),
				entity.getExceptionManager(), appendResult);

	}

	@Override
	public Query append(final Object value, final String atAddress) {
		AsyncResult<Node> appendResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				H.client(entity).runSafe(new Runnable() {

					@Override
					public void run() {

						entity.get(CallbackFactory.embeddedCallback(
								entity.getExceptionManager(), callback,
								new Closure<Node>() {

									@Override
									public void apply(Node node) {
										CoreDsl dsl = H.dsl(entity);

										OneValue<Object> appendedValue = dsl
												.append(value)
												.to(dsl.reference(node.getUri()))
												.atAddress(atAddress)
												.in(H.client(entity));

										callback.onSuccess(H
												.factory(entity)
												.createNode(
														H.session(entity),
														entity.getExceptionManager(),
														dsl.reference(appendedValue
																.getId())));
									}

								}));

					}
				});
			}
		};

		return H.factory(entity).createQuery(H.session(entity),
				entity.getExceptionManager(), appendResult);
	}

	@Override
	public Query appendValue(final Object value) {

		AsyncResult<Node> appendResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				H.client(entity).runSafe(new Runnable() {

					@Override
					public void run() {

						entity.get(CallbackFactory.embeddedCallback(
								entity.getExceptionManager(), callback,
								new Closure<Node>() {

									@Override
									public void apply(Node node) {
										CoreDsl dsl = H.dsl(entity);

										Object appended = dsl
												.append(value)
												.to(dsl.reference(node.getUri()))
												.in(H.client(entity));

										OneTypedReference<Object> appendedRef = dsl
												.reference(appended).in(
														H.client(entity));
										callback.onSuccess(H
												.factory(entity)
												.createNode(
														H.session(entity),
														entity.getExceptionManager(),
														appendedRef));
									}

								}));

					}
				});

			}

		};

		return H.factory(entity).createQuery(H.session(entity),
				entity.getExceptionManager(), appendResult);
	}

	@Override
	public <GEntity extends Entity> GEntity append(final GEntity append) {

		H.client(entity).runSafe(new Runnable() {

			@Override
			public void run() {

				entity.get(new Closure<Node>() {

					@Override
					public void apply(final Node node) {
						if (append instanceof Link) {
							performAppend(node, ((Link) append).getUri());
							return;
						}

						append.get(new Closure<Node>() {

							@Override
							public void apply(Node o) {
								performAppend(node, o.getUri());
							}

						});
					}

					private final void performAppend(Node node, String uri) {

						CoreDsl dsl = H.dsl(entity);

						dsl.append(dsl.reference(uri))
								.to(dsl.reference(node.getUri()))
								.in(H.client(entity));

					}

				});

			}

		});

		return append;
	}

	@Override
	public void injectObject(OnedbEntity obj) {
		this.entity = obj;
	}

}
