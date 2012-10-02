package io.nextweb.js;

import io.nextweb.Entity;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.operations.JsEntityOperations;
import io.nextweb.js.operations.JsEntitySetValueOperations;

public interface JsEntity<EntityType extends Entity> extends
		JsEntityOperations, JsWrapper<EntityType>, JsEntitySetValueOperations {

	public JsSession getSession();

	public JsExceptionManager getExceptionManager();

	@Override
	public EntityType getOriginal();

}
