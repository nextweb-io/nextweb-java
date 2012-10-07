package io.nextweb.js.operations.list;

import io.nextweb.Link;
import io.nextweb.js.JsLink;
import io.nextweb.js.JsLinkListQuery;
import io.nextweb.js.JsListQuery;
import io.nextweb.js.common.JsBooleanResult;

public interface JsEntityListSelectOperations {

	public JsListQuery select(JsLink propertyType);

	public JsListQuery selectAll(JsLink propertyType);

	public JsLinkListQuery selectAllLinks();

	public JsListQuery selectAll();

	public JsBooleanResult has(Link propertyType);

}
