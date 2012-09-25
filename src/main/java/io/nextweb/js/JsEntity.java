package io.nextweb.js;

import io.nextweb.Entity;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.operations.JsEntityOperations;

public interface JsEntity<EntityType extends Entity> extends
		JsEntityOperations, JsWrapper<EntityType> {

	public JsSession getSession();

	public JsExceptionManager getExceptionManager();

	public void catchExceptions(JsClosure listener);

	@Override
	public EntityType getOriginal();

}
