package io.nextweb.js;

import io.nextweb.Entity;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.operations.JsEntityAppendOperations;

public interface JsEntity<EntityType extends Entity> extends
		JsEntityAppendOperations, JsWrapper<EntityType> {

	public Object get(Object... params);

	public JsQuery select(JsLink propertyType);

	public JsNodeListQuery selectAll();

	public JsLinkListQuery selectAllLinks();

	public JsNodeListQuery selectAll(JsLink propertyType);

	public JsSession getSession();

	public JsExceptionManager getExceptionManager();

	public void catchExceptions(JsClosure listener);

	@Override
	public EntityType getOriginal();

}
