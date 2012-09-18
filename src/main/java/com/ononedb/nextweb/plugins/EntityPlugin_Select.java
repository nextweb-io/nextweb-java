package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ResultCallback;
import io.nextweb.plugins.EntityPlugin;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenChildrenSelected;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.common.OnedbEntity;

public class EntityPlugin_Select implements EntityPlugin {

	OnedbEntity entity;

	public Query select(final Link propertyType) {

		final CoreDsl dsl = H.dsl(entity);
		AsyncResult<Node> selectResult = new AsyncResult<Node>() {

			@Override
			public void get(final ResultCallback<Node> callback) {

				entity.get(new ResultCallback<Node>() {

					@Override
					public void onSuccess(Node result) {
						dsl.selectFrom(dsl.reference(result.getUri()))
								.theChildren()
								.linkingTo(dsl.reference(propertyType.getUri()))
								.in(entity.getOnedbSession().getClient())
								.and(new WhenChildrenSelected<OneTypedReference<Object>>() {

									@Override
									public void thenDo(
											WithChildrenSelectedResult<OneTypedReference<Object>> sr) {

										if (sr.children().size() == 0) {
											entity.getExceptionManager()
													.onUndefined(this);
											return;
										}

										callback.onSuccess(entity
												.getOnedbSession()
												.getOnedbEngine()
												.getFactory()
												.createNode(
														entity.getOnedbSession(),
														entity.getExceptionManager(),
														sr.nodes().get(0)));

									}

									@Override
									public void onUnauthorized(
											WithUnauthorizedContext context) {
										entity.getExceptionManager()
												.onUnauthorized(
														this,
														H.fromUnauthorizedContext(context));
									}

									@Override
									public void onFailure(Throwable t) {
										entity.getExceptionManager().onFailure(
												this, t);
									}

								});
					}

					@Override
					public void onFailure(Throwable t) {
						entity.getExceptionManager().onFailure(this, t);
					}

				});

			}

		};

		return entity
				.getOnedbSession()
				.getFactory()
				.createQuery(entity.getOnedbSession(),
						entity.getExceptionManager(), selectResult);
	}

}
