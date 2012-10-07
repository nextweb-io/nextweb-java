package io.nextweb.js.operations;

import io.nextweb.js.JsEntityList;
import io.nextweb.js.fn.JsClosure;

public interface JsEntityListEach<JsListType extends JsEntityList<?, ?>> {

	public JsListType each(final JsClosure closure);

}
