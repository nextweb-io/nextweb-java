package com.ononedb.nextweb.plugins;

import io.nextweb.Node;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.plugins.core.Plugin_Entity_ClearVersions;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenVersionsCleared;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.dsl.callbacks.results.WithVersionsClearedResult;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.common.H;

public class P_Entity_ClearVersions implements
		Plugin_Entity_ClearVersions<OnedbEntity> {

	OnedbEntity entity;

	@Override
	public Result<Integer> clearVersions(final int keepVersions) {

		AsyncResult<Integer> clearVersionsResult = new AsyncResult<Integer>() {

			@Override
			public void get(final Callback<Integer> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(Node o) {

								CoreDsl dsl = H.dsl(entity);

								System.out.println("Clearing verions ..."
										+ dsl.reference(o.getUri()) + " "
										+ keepVersions);

								dsl.clearVersions(dsl.reference(o.getUri()))
										.andKeepOnServer(keepVersions)
										.in(H.client(entity))
										.and(new WhenVersionsCleared() {

											@Override
											public void thenDo(
													WithVersionsClearedResult arg0) {
												System.out
														.println("All cleared.");
												callback.onSuccess(arg0
														.noOfVersionsCleared());
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

		Result<Integer> result = H.engine(entity).createResult(
				entity.getExceptionManager(), H.session(entity),
				clearVersionsResult);

		result.get(new Closure<Integer>() {

			@Override
			public void apply(Integer o) {
				// nothing
			}
		});

		return result;
	}

	@Override
	public void injectObject(OnedbEntity obj) {
		this.entity = obj;
	}

}
