package io.nextweb.js.operations;

import io.nextweb.js.JsEntityList;
import io.nextweb.js.operations.list.JsEntityListEach;
import io.nextweb.js.operations.list.JsEntityListSelectOperations;

public interface JsEntityListOperations<JsEntityListType extends JsEntityList<?, ?>>
		extends JsEntityListEach<JsEntityListType>,
		JsEntityListSelectOperations {

	public Object get(Object... params);

}
