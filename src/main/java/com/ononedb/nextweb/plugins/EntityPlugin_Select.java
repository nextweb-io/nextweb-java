package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ResultCallback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.EntityPlugin;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenChildrenSelected;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.common.OnedbEntity;

public class EntityPlugin_Select implements EntityPlugin {

	private final OnedbEntity entity;

	public Query select(final Link propertyType) {

		final CoreDsl dsl = H.dsl(entity);
		final ExceptionManager exceptionManager = entity.getExceptionManager();

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
											exceptionManager.onUndefined(this);
											return;
										}

										callback.onSuccess(entity
												.getOnedbSession()
												.getOnedbEngine()
												.getFactory()
												.createNode(
														entity.getOnedbSession(),
														exceptionManager,
														sr.nodes().get(0)));

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
										exceptionManager.onFailure(this, t);
									}

								});
					}

					@Override
					public void onFailure(Throwable t) {
						exceptionManager.onFailure(this, t);
					}

				});

			}

		};

		return entity
				.getOnedbSession()
				.getFactory()
				.createQuery(entity.getOnedbSession(), exceptionManager,
						selectResult);
	}

	public EntityPlugin_Select(OnedbEntity entity) {
		super();
		this.entity = entity;
	}

}
