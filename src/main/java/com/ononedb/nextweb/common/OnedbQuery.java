package com.ononedb.nextweb.common;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.AuthorizationExceptionListener;
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

	@Override
	public Query select(final Link propertyType) {
		final CoreDsl dsl = H.dsl(OnedbQuery.this);
		return new OnedbQuery(session, session.getEngine().createResult(
				new AsyncResult<Node>() {

					@Override
					public void get(ResultCallback<Node> callback) {

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

											}

											@Override
											public void onUnauthorized(
													WithUnauthorizedContext context) {
												// TODO Auto-generated method
												// stub
												super.onUnauthorized(context);
											}

											@Override
											public void onFailure(Throwable t) {
												// TODO Auto-generated method
												// stub
												super.onFailure(t);
											}

										});
							}
						});

					}

				}));
	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		return Plugins.plugin(this, factory);
	}

	public OnedbQuery(OnedbSession session, Result<Node> result) {
		super();
		this.result = result;
		this.session = session;
	}

	@Override
	public OnedbSession getOnedbSession() {

		return session;
	}

	@Override
	public void catchAuthorizationExceptions(
			AuthorizationExceptionListener listener) {

	}

	@Override
	public void catchExceptions(ExceptionListener listener) {

	}

}
