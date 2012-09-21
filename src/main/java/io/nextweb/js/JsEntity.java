package io.nextweb.js;

import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

public interface JsEntity {

	public JsQuery select(JsLink propertyType);

	public JsNodeListQuery selectAll();

	public JsLinkListQuery selectAllLinks();

	public JsNodeListQuery selectAll(JsLink propertyType);

	public JsSession getSession();

	public JsExceptionManager getExceptionManager();

	public void catchExceptions(JsClosure listener);

}
