package io.nextweb.js;

import io.nextweb.EntityList;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.operations.JsEntityListOperations;

public interface JsEntityList<EntityListType extends EntityList, JsListType extends JsEntityList<EntityListType, ?>>
		extends JsEntityListOperations<JsListType>, JsWrapper<EntityListType> {

	public JsSession getSession();

	public JsExceptionManager getExceptionManager();

}
