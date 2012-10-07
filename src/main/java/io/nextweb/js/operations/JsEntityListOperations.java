package io.nextweb.js.operations;

import io.nextweb.js.JsEntityList;
import io.nextweb.js.operations.list.JsEntityListEach;

public interface JsEntityListOperations<JsEntityListType extends JsEntityList<?, ?>>
		extends JsEntityListEach<JsEntityListType> {

	public Object get(Object... params);

}
