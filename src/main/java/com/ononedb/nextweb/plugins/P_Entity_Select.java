package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.core.Plugin_Entity_Select;

import java.util.ArrayList;
import java.util.List;

import one.core.domain.OneClass;
import one.core.domain.OneTypedMatcher;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenChildrenSelected;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.common.H;

public class P_Entity_Select implements Plugin_Entity_Select<OnedbEntity> {

	private OnedbEntity entity;

	@Override
	public NodeListQuery selectAll(final Link propertyType) {
		final CoreDsl dsl = H.dsl(entity);

		final ExceptionManager exceptionManager = entity.getExceptionManager();

		AsyncResult<NodeList> selectAllResult = new AsyncResult<NodeList>() {

			@Override
			public void get(final Callback<NodeList> callback) {
				entity.get(CallbackFactory.embeddedCallback(exceptionManager,
						callback, new Closure<Node>() {

							@Override
							public void apply(Node result) {
								dsl.selectFrom(H.node(dsl, result))
										.theChildren()
										.linkingTo(
												dsl.reference(propertyType
														.getUri()))
										.in(H.client(entity))
										.and(new WhenChildrenSelected<OneTypedReference<Object>>() {

											@Override
											public void thenDo(
													WithChildrenSelectedResult<OneTypedReference<Object>> sr) {

												List<Node> nodes = new ArrayList<Node>(
														sr.children().size());

												for (OneTypedReference<?> child : sr
														.children()) {

													nodes.add(H
															.factory(entity)
															.createNode(
																	H.session(entity),
																	exceptionManager,
																	child));

												}

												callback.onSuccess(H
														.factory(entity)
														.createNodeList(
																H.session(entity),
																exceptionManager,
																nodes));
											}

											@Override
											public void onUnauthorized(
													WithUnauthorizedContext context) {
												callback.onUnauthorized(
														this,
														H.fromUnauthorizedContext(context));

											}

											@Override
											public void onFailure(Throwable t) {
												callback.onFailure(this, t);
											}

										});

							}

						}));
			}

		};

		return H.factory(entity).createNodeListQuery(H.session(entity),
				exceptionManager, selectAllResult);
	}

	@Override
	public Query select(final Link propertyType) {

		final CoreDsl dsl = H.dsl(entity);
		final ExceptionManager exceptionManager = entity.getExceptionManager();

		AsyncResult<Node> selectResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				entity.get(CallbackFactory.embeddedCallback(exceptionManager,
						callback, new Closure<Node>() {

							@Override
							public void apply(Node result) {

								dsl.selectFrom(dsl.reference(result.getUri()))
										.theChildren()
										.linkingTo(
												dsl.reference(propertyType
														.getUri()))
										.in(H.client(entity))
										.and(new WhenChildrenSelected<OneTypedReference<Object>>() {

											@Override
											public void thenDo(
													WithChildrenSelectedResult<OneTypedReference<Object>> sr) {

												if (sr.children().size() == 0) {
													callback.onUndefined(this,
															"No child matching the specified criteria.");
													return;
												}

												callback.onSuccess(H
														.factory(entity)
														.createNode(
																entity.getOnedbSession(),
																exceptionManager,
																sr.nodes().get(
																		0)));

											}

											@Override
											public void onUnauthorized(
													WithUnauthorizedContext context) {
												callback.onUnauthorized(
														this,
														H.fromUnauthorizedContext(context));
											}

											@Override
											public void onFailure(Throwable t) {
												callback.onFailure(this, t);
											}

										});
							}

						}));

			}
		};
		return H.factory(entity).createQuery(entity.getOnedbSession(),
				exceptionManager, selectResult);
	}

	public P_Entity_Select() {
		super();
	}

	@Override
	public LinkListQuery selectAllLinks() {
		final CoreDsl dsl = H.dsl(entity);

		final ExceptionManager exceptionManager = entity.getExceptionManager();

		AsyncResult<LinkList> selectAllLinksResult = new AsyncResult<LinkList>() {

			@Override
			public void get(final Callback<LinkList> callback) {
				entity.get(CallbackFactory.embeddedCallback(exceptionManager,
						callback, new Closure<Node>() {

							@Override
							public void apply(Node result) {
								try {
									List<String> children = dsl
											.selectFrom(
													dsl.reference(result
															.getUri()))
											.allChildrenFast()
											.in(H.client(entity));

									List<Link> linkList = new ArrayList<Link>(
											children.size());

									for (String uri : children) {
										linkList.add(H.factory(entity)
												.createLink(H.session(entity),
														exceptionManager, uri));
									}

									callback.onSuccess(H.factory(entity)
											.createLinkList(H.session(entity),
													exceptionManager, linkList));
								} catch (Throwable t) {
									callback.onFailure(this, t);
								}
							}

						}));

			}

		};

		return H.factory(entity).createLinkListQuery(H.session(entity),
				exceptionManager, selectAllLinksResult);
	}

	@Override
	public NodeListQuery selectAll() {
		final CoreDsl dsl = H.dsl(entity);

		final ExceptionManager exceptionManager = entity.getExceptionManager();

		AsyncResult<NodeList> selectAllResult = new AsyncResult<NodeList>() {

			@Override
			public void get(final Callback<NodeList> callback) {
				entity.get(CallbackFactory.embeddedCallback(exceptionManager,
						callback, new Closure<Node>() {

							@Override
							public void apply(final Node result) {
								dsl.selectFrom(H.node(dsl, result))
										.theChildren()
										.satisfying(
												new OneTypedMatcher<Object>() {

													@Override
													public OneClass<Object> getValueType() {
														return new OneClass<Object>() {

															@Override
															public Class<Object> getType() {

																return Object.class;
															}

															@Override
															public boolean test(
																	Object arg0) {
																return arg0 instanceof Object;
															}

														};
													}

													@Override
													public boolean matches(
															Object arg0) {
														return true;
													}
												})
										.in(H.client(entity))
										.and(new WhenChildrenSelected<OneTypedReference<Object>>() {

											@Override
											public void thenDo(
													WithChildrenSelectedResult<OneTypedReference<Object>> sr) {

												List<Node> nodes = new ArrayList<Node>(
														sr.children().size());

												for (OneTypedReference<?> child : sr
														.children()) {

													nodes.add(H
															.factory(entity)
															.createNode(
																	H.session(entity),
																	exceptionManager,
																	child));

												}

												callback.onSuccess(H
														.factory(entity)
														.createNodeList(
																H.session(entity),
																exceptionManager,
																nodes));
											}

											@Override
											public void onUnauthorized(
													WithUnauthorizedContext context) {
												callback.onUnauthorized(
														this,
														H.fromUnauthorizedContext(context));
											}

											@Override
											public void onFailure(Throwable t) {
												callback.onFailure(this, t);
											}

										});

							}

						}));

			}

		};

		return H.factory(entity).createNodeListQuery(H.session(entity),
				exceptionManager, selectAllResult);
	}

	@Override
	public void injectObject(OnedbEntity obj) {
		this.entity = obj;

	}

}
