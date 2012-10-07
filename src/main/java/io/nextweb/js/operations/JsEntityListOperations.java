package io.nextweb.js.operations;

import io.nextweb.js.JsEntityList;

public interface JsEntityListOperations<JsEntityListType extends JsEntityList<?, ?>>
		extends JsEntityListEach<JsEntityListType> {

	public Object get(Object... params);

}
