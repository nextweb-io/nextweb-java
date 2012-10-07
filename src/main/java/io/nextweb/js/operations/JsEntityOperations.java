package io.nextweb.js.operations;

import io.nextweb.js.JsLink;
import io.nextweb.js.JsLinkListQuery;
import io.nextweb.js.JsListQuery;
import io.nextweb.js.JsQuery;
import io.nextweb.js.operations.entity.JsEntityAppendOperations;
import io.nextweb.js.operations.entity.JsEntityClearVersionsOperations;
import io.nextweb.js.operations.entity.JsEntityRemoveOperations;

public interface JsEntityOperations extends JsEntityAppendOperations,
		JsEntityRemoveOperations, JsEntityClearVersionsOperations {

	public Object get(Object... params);

	public JsQuery select(JsLink propertyType);

	public JsListQuery selectAll();

	public JsLinkListQuery selectAllLinks();

	public JsListQuery selectAll(JsLink propertyType);

}
