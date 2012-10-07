package io.nextweb.js.operations.list;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.fn.BooleanResult;
import io.nextweb.js.JsLink;
import io.nextweb.js.JsListQuery;

public interface JsEntityListSelectOperations {

	public JsListQuery select(JsLink propertyType);

	public JsListQuery selectAll(JsLink propertyType);

	public LinkListQuery selectAllLinks();

	public ListQuery selectAll();

	public BooleanResult has(Link propertyType);

}
