package io.nextweb.js.operations;

import io.nextweb.js.JsLink;
import io.nextweb.js.JsLinkListQuery;
import io.nextweb.js.JsNodeListQuery;
import io.nextweb.js.JsQuery;

public interface JsEntityOperations extends JsEntityAppendOperations,
		JsEntityRemoveOperations, JsClearVersionsOperations {

	public Object get(Object... params);

	public JsQuery select(JsLink propertyType);

	public JsNodeListQuery selectAll();

	public JsLinkListQuery selectAllLinks();

	public JsNodeListQuery selectAll(JsLink propertyType);

}
