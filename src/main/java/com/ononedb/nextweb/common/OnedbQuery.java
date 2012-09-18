package com.ononedb.nextweb.common;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.AuthorizationExceptionListener;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.Plugins;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenChildrenSelected;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.OnedbSession;

public class OnedbQuery implements Query, OnedbObject {

	private final Result<Node> result;
	private final OnedbSession session;
	private final ExceptionManager exceptionManager;

	@Override
	public Query select(final Link propertyType) {
		final CoreDsl dsl = H.dsl(OnedbQuery.this);
		Result<Node> selectResult = session.getEngine().createResult(
				new AsyncResult<Node>() {

					@Override
					public void get(final ResultCallback<Node> callback) {

						result.get(new ResultCallback<Node>() {

							@Override
							public void onSuccess(Node result) {
								dsl.selectFrom(dsl.reference(result.getUri()))
										.theChildren()
										.linkingTo(
												dsl.reference(propertyType
														.getUri()))
										.in(session.getClient())
										.and(new WhenChildrenSelected<OneTypedReference<Object>>() {

											@Override
											public void thenDo(
													WithChildrenSelectedResult<OneTypedReference<Object>> sr) {

												if (sr.children().size() == 0) {
													exceptionManager
															.onUndefined(this);
													return;
												}

												callback.onSuccess(new OnedbNode(
														getOnedbSession(), sr
																.nodes().get(0)));

											}

											@Override
											public void onUnauthorized(
													WithUnauthorizedContext context) {
												exceptionManager.onUnauthorized(
														this,
														H.fromUnauthorizedContext(context));
											}

											@Override
											public void onFailure(Throwable t) {
												exceptionManager.onFailure(
														this, t);
											}

										});
							}

							@Override
							public void onFailure(Throwable t) {
								exceptionManager.onFailure(this, t);
							}

						});

					}

				});

		return session.getFactory().createQuery(exceptionManager, selectResult);
	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	public OnedbQuery(OnedbSession session,
			ExceptionManager fallbackExceptionManager, Result<Node> result) {
		super();
		this.result = result;
		this.session = session;

		this.exceptionManager = new ExceptionManager(fallbackExceptionManager);
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {
		this.exceptionManager.catchAuthorizationExceptions(listener);
	}

	@Override
	public void catchExceptions(ExceptionListener listener) {
		this.exceptionManager.catchExceptions(listener);
	}

	@Override
	public void catchUndefinedExceptions(UndefinedExceptionListener listener) {
		this.exceptionManager.catchUndefinedExceptions(listener);

	}

}
