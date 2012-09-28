package com.ononedb.nextweb.plugins;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Fn;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.plugins.core.Plugin_Entity_Append;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenResponseFromServerReceived;
import one.core.dsl.callbacks.results.WithImpossibleContext;
import one.core.dsl.callbacks.results.WithOperationResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;
import one.core.nodes.OneValue;
import one.utils.OneUtilsStrings;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.OnedbQuery;
import com.ononedb.nextweb.common.H;

public class P_Entity_Append implements Plugin_Entity_Append<OnedbEntity> {

	OnedbEntity entity;

	@Override
	public Query append(final Object value) {
		return executeAppend(value, null, false, -1, false, false);
	}

	@Override
	public Query append(final Object value, final String atAddress) {
		return executeAppend(value, atAddress, false, -1, false, false);
	}

	@Override
	public Query appendValue(final Object value) {
		return executeAppend(value, null, false, -1, false, true);
	}

	@Override
	public Query appendSafe(final Object value) {
		return executeAppend(value, null, false, -1, true, false);
	}

	@Override
	public Query appendSafe(final Object value, final String atAddress) {
		return executeAppend(value, atAddress, false, -1, true, false);
	}

	@Override
	public Query appendValueSafe(final Object value) {
		return executeAppend(value, null, false, -1, true, true);
	}

	@Override
	public Query appendSafe(final Entity append) {
		return executeAppendEntity(append, false, -1, true);
	}

	@Override
	public Query append(final Entity append) {
		return executeAppendEntity(append, false, -1, false);
	}

	@Override
	public Query insert(final Object value, final int atIndex) {
		return executeAppend(value, null, true, atIndex, false, false);
	}

	@Override
	public Query insert(final Object value, final String atAddress,
			final int atIndex) {
		return executeAppend(value, atAddress, true, atIndex, false, false);
	}

	@Override
	public Query insertValue(final Object value, final int atIndex) {
		return executeAppend(value, null, true, atIndex, false, true);
	}

	@Override
	public Query insert(final Entity entity, final int atIndex) {
		return executeAppendEntity(entity, true, atIndex, false);
	}

	@Override
	public Query insertSafe(final Object value, final int atIndex) {
		return executeAppend(value, null, true, atIndex, true, false);
	}

	@Override
	public Query insertSafe(final Object value, final String atAddress,
			final int atIndex) {
		return executeAppend(value, atAddress, true, atIndex, true, false);
	}

	@Override
	public Query insertValueSafe(final Object value, final int atIndex) {
		return executeAppend(value, null, true, atIndex, true, true);
	}

	@Override
	public Query insertSafe(final Entity entity, final int atIndex) {
		return executeAppendEntity(entity, true, atIndex, true);
	}

	private final Query executeAppend(final Object value,
			final String p_address, final boolean isInsert, final int index,
			final boolean isSafe, final boolean appendAsValue) {

		final AsyncResult<Node> appendResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(final Node node) {

								H.engine(entity).runSafe(H.session(entity),
										new Runnable() {

											@Override
											public void run() {

												final CoreDsl dsl = H
														.dsl(entity);

												String address = p_address;

												if (address == null) {
													final String generatedAddress = generateAddress(
															value, node, dsl);

													address = "./"
															+ generatedAddress;
												}

												if (!isSafe) {

													executeUnsafeAppendInsert(
															value, isInsert,
															index,
															appendAsValue,
															callback, node,
															dsl, address);
													return;

												} else {

													executeSafeAppendInsert(
															value, isInsert,
															index,
															appendAsValue,
															callback, node,
															dsl, address);

													return;
												}
											}

										});
							}

						}));

			}

		};

		final OnedbQuery appendQuery = H.factory(entity).createQuery(
				H.session(entity), entity.getExceptionManager(), appendResult);

		appendQuery.get(new Closure<Node>() {

			@Override
			public void apply(final Node o) {
				// nothing
			}

		});

		return appendQuery;

	}

	private final void executeSafeAppendInsert(final Object value,
			final boolean isInsert, final int index,
			final boolean appendAsValue, final Callback<Node> callback,
			final Node node, final CoreDsl dsl, final String address) {
		if (!appendAsValue) {

			final OneValue<Object> appendType = null;

			if (!isInsert) {

				dsl.appendSafe(value)
						.to(dsl.reference(node.getUri()))
						.atAddress(address)
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								appendType, node.getSecret()));
			} else {
				dsl.insertSafe(value)
						.to(dsl.reference(node.getUri()))
						.atIndex(index)
						.atAddress(address)
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								appendType, node.getSecret()));
			}

			return;
		} else {

			if (!isInsert) {
				dsl.appendSafe(value)
						.to(dsl.reference(node.getUri()))
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								value, node.getSecret()));
			} else {
				dsl.insertSafe(value)
						.to(dsl.reference(node.getUri()))
						.atIndex(index)
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								value, node.getSecret()));

			}

			return;

		}
	}

	private <GType extends Object> WhenResponseFromServerReceived<GType> createCallbackForSafeOperation(
			final Callback<Node> callback, final CoreDsl dsl, final GType type,
			final String secret) {
		return new WhenResponseFromServerReceived<GType>() {

			@Override
			public void thenDo(final WithOperationResult<GType> or) {
				callback.onSuccess(H.factory(entity).createNode(
						H.session(entity), entity.getExceptionManager(),
						dsl.reference(or.node().getId()), secret));
			}

			@Override
			public void onUnauthorized(final WithUnauthorizedContext context) {
				callback.onUnauthorized(H
						.fromUnauthorizedContext(this, context));
			}

			@Override
			public void onImpossible(final WithImpossibleContext context) {
				callback.onImpossible(H.fromImpossibleContext(this, context));
			}

			@Override
			public void onFailure(final Throwable t) {
				callback.onFailure(Fn.exception(this, t));
			}

		};
	}

	private final void executeUnsafeAppendInsert(final Object value,
			final boolean isInsert, final int index,
			final boolean appendAsValue, final Callback<Node> callback,
			final Node node, final CoreDsl dsl, final String address) {
		if (!appendAsValue) {

			OneValue<?> appendedValue;

			if (!isInsert) {
				appendedValue = dsl.append(value)
						.to(dsl.reference(node.getUri())).atAddress(address)
						.in(H.client(entity));
			} else {
				appendedValue = dsl.insert(value)
						.to(dsl.reference(node.getUri())).atIndex(index)
						.atAddress(address).in(H.client(entity));
			}

			callback.onSuccess(H.factory(entity).createNode(H.session(entity),
					entity.getExceptionManager(),
					dsl.reference(appendedValue.getId()), node.getSecret()));
			return;
		} else {

			Object appended;
			if (!isInsert) {
				appended = dsl.append(value).to(dsl.reference(node.getUri()))
						.in(H.client(entity));
			} else {
				appended = dsl.insert(value).to(dsl.reference(node.getUri()))
						.atIndex(index).in(H.client(entity));

			}

			final OneTypedReference<Object> appendedRef = dsl.reference(
					appended).in(H.client(entity));
			callback.onSuccess(H.factory(entity)
					.createNode(H.session(entity),
							entity.getExceptionManager(), appendedRef,
							node.getSecret()));
			return;

		}
	}

	private String generateAddress(final Object value, final Node node,
			final CoreDsl dsl) {
		String genAddress = value.toString();

		if (genAddress.length() > 8) {
			genAddress = genAddress.substring(0, 7);
		}

		String cleanedString = "";
		for (int i = 0; i <= genAddress.length() - 1; i++) {
			if (!OneUtilsStrings.isSimpleCharacter(genAddress.charAt(i))) {
				cleanedString = cleanedString + "_";
			} else {
				cleanedString = cleanedString + genAddress.charAt(i);
			}
		}

		cleanedString = cleanedString
				+ (dsl.selectFrom(dsl.reference(node.getUri()))
						.allChildrenFast().in(H.client(entity)).size() + 1);
		return cleanedString;
	}

	private final Query executeAppendEntity(final Entity append,
			final boolean isInsert, final int index, final boolean isSafe) {

		final AsyncResult<Node> appendResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {
				H.client(entity).runSafe(new Runnable() {

					@Override
					public void run() {

						entity.get(CallbackFactory.embeddedCallback(
								entity.getExceptionManager(), callback,
								new Closure<Node>() {

									@Override
									public void apply(final Node node) {
										if (append instanceof Link) {
											performAppend(node,
													((Link) append).getUri());
											return;
										}

										append.get(new Closure<Node>() {

											@Override
											public void apply(final Node o) {
												performAppend(node, o.getUri());
											}

										});
									}

									private final void performAppend(
											final Node node, final String uri) {

										final CoreDsl dsl = H.dsl(entity);

										if (!isSafe) {

											if (!isInsert) {
												dsl.append(dsl.reference(uri))
														.to(dsl.reference(node
																.getUri()))
														.in(H.client(entity));
											} else {
												dsl.insert(dsl.reference(uri))
														.to(dsl.reference(node
																.getUri()))
														.atIndex(index)
														.in(H.client(entity));
											}

											callback.onSuccess(H
													.factory(entity)
													.createNode(
															H.session(entity),
															entity.getExceptionManager(),
															dsl.reference(uri),
															node.getSecret()));
											return;

										} else {

											@SuppressWarnings("unchecked")
											final OneTypedReference<Object> onedbNodeToAppend = (OneTypedReference<Object>) dsl
													.reference(uri);

											if (!isInsert) {

												dsl.appendSafe(
														onedbNodeToAppend)
														.to(dsl.reference(node
																.getUri()))
														.in(H.client(entity))
														.and(createCallbackForSafeOperation(
																callback,
																dsl,
																onedbNodeToAppend,
																node.getSecret()));
												return;
											} else {

												dsl.insertSafe(
														onedbNodeToAppend)
														.to(dsl.reference(node
																.getUri()))
														.atIndex(index)
														.in(H.client(entity))
														.and(createCallbackForSafeOperation(
																callback,
																dsl,
																onedbNodeToAppend,
																node.getSecret()));
												return;

											}

										}

									}

								}));

					}

				});
			}
		};

		final OnedbQuery appendQuery = H.factory(entity).createQuery(
				H.session(entity), entity.getExceptionManager(), appendResult);

		appendQuery.get(new Closure<Node>() {

			@Override
			public void apply(final Node o) {
				// nothing
			}

		});

		return appendQuery;
	}

	@Override
	public void injectObject(final OnedbEntity obj) {
		this.entity = obj;
	}

}
