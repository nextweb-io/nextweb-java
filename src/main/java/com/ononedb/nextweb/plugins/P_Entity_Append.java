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

	private final Query executeAppend(final Object value,
			final String p_address, final boolean isInsert, final int index,
			final boolean isSafe, final boolean appendAsValue) {

		AsyncResult<Node> appendResult = new AsyncResult<Node>() {

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

												CoreDsl dsl = H.dsl(entity);

												String address = p_address;

												if (address == null) {
													String generatedAddress = generateAddress(
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

		OnedbQuery appendQuery = H.factory(entity).createQuery(
				H.session(entity), entity.getExceptionManager(), appendResult);

		appendQuery.get(new Closure<Node>() {

			@Override
			public void apply(Node o) {
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

			OneValue<Object> appendType = null;

			if (!isInsert) {

				dsl.appendSafe(value)
						.to(dsl.reference(node.getUri()))
						.atAddress(address)
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								appendType));
			} else {
				dsl.insertSafe(value)
						.to(dsl.reference(node.getUri()))
						.atIndex(index)
						.atAddress(address)
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								appendType));
			}

			return;
		} else {

			if (!isInsert) {
				dsl.appendSafe(value)
						.to(dsl.reference(node.getUri()))
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								value));
			} else {
				dsl.insertSafe(value)
						.to(dsl.reference(node.getUri()))
						.atIndex(index)
						.in(H.client(entity))
						.and(createCallbackForSafeOperation(callback, dsl,
								value));

			}

			return;

		}
	}

	private <GType extends Object> WhenResponseFromServerReceived<GType> createCallbackForSafeOperation(
			final Callback<Node> callback, final CoreDsl dsl, final GType type) {
		return new WhenResponseFromServerReceived<GType>() {

			@Override
			public void thenDo(WithOperationResult<GType> or) {
				callback.onSuccess(H.factory(entity).createNode(
						H.session(entity), entity.getExceptionManager(),
						dsl.reference(or.node().getId())));
			}

			@Override
			public void onUnauthorized(WithUnauthorizedContext context) {
				callback.onUnauthorized(H
						.fromUnauthorizedContext(this, context));
			}

			@Override
			public void onImpossible(WithImpossibleContext context) {
				callback.onImpossible(H.fromImpossibleContext(this, context));
			}

			@Override
			public void onFailure(Throwable t) {
				callback.onFailure(Fn.exception(this, t));
			}

		};
	}

	private final void executeUnsafeAppendInsert(final Object value,
			final boolean isInsert, final int index,
			final boolean appendAsValue, final Callback<Node> callback,
			final Node node, CoreDsl dsl, String address) {
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
					dsl.reference(appendedValue.getId())));
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

			OneTypedReference<Object> appendedRef = dsl.reference(appended).in(
					H.client(entity));
			callback.onSuccess(H.factory(entity).createNode(H.session(entity),
					entity.getExceptionManager(), appendedRef));
			return;

		}
	}

	private String generateAddress(final Object value, final Node node,
			CoreDsl dsl) {
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
