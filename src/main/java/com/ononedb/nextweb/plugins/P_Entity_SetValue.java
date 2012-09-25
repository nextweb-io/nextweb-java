package com.ononedb.nextweb.plugins;

import one.core.dsl.CoreDsl;
import one.core.nodes.OneValue;
import io.nextweb.Node;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.plugins.core.Plugin_Entity_SetValue;

import com.ononedb.nextweb.OnedbEntity;
import com.ononedb.nextweb.OnedbNode;
import com.ononedb.nextweb.common.H;

public class P_Entity_SetValue implements Plugin_Entity_SetValue<OnedbEntity> {

	OnedbEntity entity;

	@Override
	public Result<Success> setValueSafe(Object newValue) {

		AsyncResult<Success> setValueResult = new AsyncResult<Success>() {

			@Override
			public void get(Callback<Success> callback) {

				entity.get(CallbackFactory.embeddedCallback(
						entity.getExceptionManager(), callback,
						new Closure<Node>() {

							@Override
							public void apply(Node o) {
								foreijf
							}
						}));

			}
		};

		return H.engine(entity).createResult(entity.getExceptionManager(),
				entity.getSession(), setValueResult);
	}

	@Override
	public OnedbEntity setValue(Object newValue) {

		entity.get(new Closure<Node>() {

			@Override
			public void apply(Node o) {
				H.engine(entity).runSafe(H.session(this), new Runnable() {

					@Override
					public void run() {

						Object dereferenced = H.dsl(entity).dereference(node)
								.in(session.getClient());

						CoreDsl dsl = H.dsl(entity);
						if (dereferenced instanceof OneValue<?>) {

							OneValue<?> newValueObject = dsl.newNode(newValue)
									.at(getUri());

							dsl.replace(node).with(newValueObject)
									.in(H.client(OnedbNode.this));

							return;
						}

						dsl.replace(node).with(newValue)
								.in(H.client(OnedbNode.this));
					}
				});
			}
		});

		return entity;
	}

	@Override
	public void injectObject(OnedbEntity obj) {
		this.entity = obj;
	}

}
