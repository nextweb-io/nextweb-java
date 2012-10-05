package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.BasicResult;
import io.nextweb.fn.BooleanResult;
import io.nextweb.fn.Calculation;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Maybe;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;
import io.nextweb.plugins.core.Plugin_EntityList_Select;

import java.util.ArrayList;
import java.util.List;

import one.async.joiner.ListCallback;
import one.async.joiner.ListCallbackJoiner;
import one.async.joiner.LocalCallback;

import com.ononedb.nextweb.OnedbEntityList;
import com.ononedb.nextweb.common.H;

public class P_EntityList_Select implements
		Plugin_EntityList_Select<OnedbEntityList> {

	private OnedbEntityList entity;

	public P_EntityList_Select() {
		super();

	}

	@Override
	public void injectObject(final OnedbEntityList obj) {
		this.entity = obj;
	}

	@Override
	public ListQuery select(final Link propertyType) {

		final Calculation<Node, Query> queryOperation = new Calculation<Node, Query>() {

			@Override
			public Query apply(final Node input) {
				return input.select(propertyType);
			}
		};

		return joinNodeResults(queryOperation);
	}

	/**
	 * Will apply the specified query for all items in the list and join the
	 * results.
	 * 
	 * @param operation
	 * @return
	 */
	private ListQuery joinNodeResults(final Calculation<Node, Query> operation) {

		return joinGeneric(operation,
				new Calculation<List<Maybe<Node>>, NodeList>() {

					@Override
					public NodeList apply(final List<Maybe<Node>> input) {
						return H.factory(entity).createNodeList(
								H.session(entity),
								entity.getExceptionManager(),
								Maybe.allValues(input));
					}
				}, new Calculation<AsyncResult<NodeList>, ListQuery>() {

					@Override
					public ListQuery apply(final AsyncResult<NodeList> input) {
						return H.factory(entity).createNodeListQuery(
								H.session(entity),
								entity.getExceptionManager(), input);
					}
				});
	}

	/**
	 * Will apply the specified query for all items in the list and join the
	 * results.
	 * 
	 * @param operation
	 * @return
	 */
	private ListQuery joinListResults(
			final Calculation<Node, ListQuery> operation) {

		return joinGeneric(operation,
				new Calculation<List<Maybe<NodeList>>, NodeList>() {

					@Override
					public NodeList apply(final List<Maybe<NodeList>> input) {
						final List<NodeList> lists = Maybe.allValues(input);

						final List<Node> nodes = new ArrayList<Node>();

						for (final NodeList list : lists) {
							nodes.addAll(list.asList());
						}

						return H.factory(entity).createNodeList(
								H.session(entity),
								entity.getExceptionManager(), nodes);
					}
				}, new Calculation<AsyncResult<NodeList>, ListQuery>() {

					@Override
					public ListQuery apply(final AsyncResult<NodeList> input) {
						return H.factory(entity).createNodeListQuery(
								H.session(entity),
								entity.getExceptionManager(), input);
					}
				});
	}

	private <QueryResultType extends BasicResult<JoinType>, JoinType, JoinedType, ResultType> ResultType joinGeneric(
			final Calculation<Node, QueryResultType> operation,
			final Calculation<List<Maybe<JoinType>>, JoinedType> joinOperation,
			final Calculation<AsyncResult<JoinedType>, ResultType> createResultOperation) {
		final AsyncResult<JoinedType> selectResult = new AsyncResult<JoinedType>() {

			@Override
			public void get(final Callback<JoinedType> callback) {
				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<NodeList>() {

							@Override
							public void apply(final NodeList o) {

								final ListCallbackJoiner<Node, Maybe<JoinType>> joiner = new ListCallbackJoiner<Node, Maybe<JoinType>>(
										o.asList(),
										new ListCallback<Maybe<JoinType>>() {

											@Override
											public void onFailure(
													final Throwable arg0) {
												callback.onFailure(Fn
														.exception(this, arg0));
											}

											@Override
											public void onSuccess(
													final List<Maybe<JoinType>> maybeLists) {

												callback.onSuccess(joinOperation
														.apply(maybeLists));
											}
										});

								for (final Node child : o) {

									final LocalCallback<Maybe<JoinType>> localCallback = joiner
											.createCallback(child);

									final QueryResultType queryResult = operation
											.apply(child);

									queryResult.get(CallbackFactory
											.eagerCallback(
													H.session(entity),
													entity.getExceptionManager(),
													new Closure<JoinType>() {

														@Override
														public void apply(
																final JoinType o) {
															localCallback
																	.onSuccess(Maybe
																			.is(o));
														}

													})
											.catchExceptions(
													new ExceptionListener() {

														@Override
														public void onFailure(
																final ExceptionResult r) {
															localCallback
																	.onFailure(r
																			.exception());
														}
													})
											.catchUnauthorized(
													new UnauthorizedListener() {

														@SuppressWarnings("unchecked")
														@Override
														public void onUnauthorized(
																final UnauthorizedResult r) {
															localCallback
																	.onSuccess((Maybe<JoinType>) Maybe
																			.isNot());
														}
													})
											.catchUndefined(
													new UndefinedListener() {

														@SuppressWarnings("unchecked")
														@Override
														public void onUndefined(
																final UndefinedResult r) {
															localCallback
																	.onSuccess((Maybe<JoinType>) Maybe
																			.isNot());
														}
													}));

								}

							}

						}));

			}
		};

		return createResultOperation.apply(selectResult);
	}

	@Override
	public ListQuery selectAll(final Link propertyType) {
		final Calculation<Node, ListQuery> queryOperation = new Calculation<Node, ListQuery>() {

			@Override
			public ListQuery apply(final Node input) {
				return input.selectAll(propertyType);
			}
		};

		return joinListResults(queryOperation);
	}

	@Override
	public LinkListQuery selectAllLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListQuery selectAll() {
		final Calculation<Node, ListQuery> queryOperation = new Calculation<Node, ListQuery>() {

			@Override
			public ListQuery apply(final Node input) {
				return input.selectAll();
			}
		};

		return joinListResults(queryOperation);
	}

	@Override
	public BooleanResult has(final Link propertyType) {
		// TODO Auto-generated method stub
		return null;
	}

}
