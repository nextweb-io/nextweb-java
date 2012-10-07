package io.nextweb.js.operations;

import io.nextweb.js.JsEntityList;
import io.nextweb.js.operations.list.JsEntityListEach;
import io.nextweb.js.operations.list.JsEntityListSelectOperations;
import io.nextweb.js.operations.list.JsEntityListSetValue;

public interface JsEntityListOperations<JsEntityListType extends JsEntityList<?, ?>>
		extends JsEntityListEach<JsEntityListType>,
		JsEntityListSelectOperations, JsEntityListSetValue {

	public Object get(Object... params);

}
