package io.nextweb.js.fn;

import io.nextweb.fn.BasicResult;
import io.nextweb.js.JsWrapper;
import io.nextweb.js.operations.JsExceptionListeners;

import org.timepedia.exporter.client.Exportable;

public interface JsBaseResult<ResultType extends JsBaseResult<?>> extends
		JsExceptionListeners<ResultType>, JsWrapper<BasicResult<Object>>,
		Exportable {

	public Object get(final Object... params);

}
