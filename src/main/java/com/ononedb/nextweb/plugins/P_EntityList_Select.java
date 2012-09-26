package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.ListQuery;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.plugins.core.Plugin_EntityList_Select;

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
	public void injectObject(OnedbEntityList obj) {
		this.entity = obj;
	}

	@Override
	public ListQuery select(final Link propertyType) {
		AsyncResult<NodeList> selectResult = new AsyncResult<NodeList>() {

			@Override
			public void get(final Callback<NodeList> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<NodeList>() {

							@Override
							public void apply(NodeList nodeList) {
								ListCallbackJoiner<Node, Node> joiner = new ListCallbackJoiner<Node, Node>(
										nodeList.asList(),
										new ListCallback<Node>() {

											@Override
											public void onFailure(Throwable arg0) {
												callback.onFailure(Fn
														.exception(this, arg0));
											}

											@Override
											public void onSuccess(
													List<Node> nodes) {

												callback.onSuccess(H
														.factory(entity)
														.createNodeList(
																H.session(entity),
																entity.getExceptionManager(),
																nodes));
											}
										});

								for (Node child : nodeList) {

									final LocalCallback<Node> localCallback = joiner
											.createCallback(child);

									child.select(propertyType)
											.catchExceptions(
													new ExceptionListener() {

														@Override
														public void onFailure(
																ExceptionResult r) {

															localCallback
																	.onFailure(r
																			.exception());
														}
													})
											.get(CallbackFactory
													.eagerCallback(
															H.session(entity),
															entity.getExceptionManager(),
															new Closure<Node>() {

																@Override
																public void apply(
																		Node o) {
																	localCallback
																			.onSuccess(o);
																}

															})
													.catchFailures(
															new ExceptionListener() {

																@Override
																public void onFailure(
																		ExceptionResult r) {
																	localCallback
																			.onFailure(r
																					.exception());
																}
															}));

								}
							}
						}));

			}
		};

		return H.factory(entity).createNodeListQuery(H.session(entity),
				entity.getExceptionManager(), selectResult);
	}

	@Override
	public ListQuery selectAll(Link propertyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkListQuery selectAllLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListQuery selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
