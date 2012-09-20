package io.nextweb.js.engine;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.js.utils.WrapperCollection;

public interface NextwebEngineJs extends NextwebEngine {

	public Object wrapForJs(Object in);

	public WrapperCollection getWrappers();

}
