package io.nextweb.js.common;

import io.nextweb.Session;
import io.nextweb.fn.BasicResult;
import io.nextweb.fn.BooleanResult;
import io.nextweb.js.fn.JsBaseResult;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsBooleanResult implements JsBaseResult<JsBooleanResult>,
		Exportable {

	JsResult jsResult;
	BooleanResult booleanResult;

	public JsBooleanResult() {
		super();
	}

	@NoExport
	public void setJsResult(final JsResult jsResult) {
		this.jsResult = jsResult;
	}

	@NoExport
	public void setBooleanResult(final BooleanResult booleanResult) {
		this.booleanResult = booleanResult;
	}

	@NoExport
	@Override
	public BasicResult<Object> getOriginal() {
		return jsResult.getOriginal();
	}

	@NoExport
	@Override
	public void setOriginal(final BasicResult<Object> original) {
		jsResult.setOriginal(original);
	}

	@NoExport
	public static JsBooleanResult wrap(final BooleanResult booleanResult,
			final Session session) {
		final JsBooleanResult jsResult = new JsBooleanResult();
		jsResult.setBooleanResult(booleanResult);
		jsResult.setJsResult(JH.jsFactory(session).createResult(booleanResult));
		return jsResult;
	}

	/*
	 * Base result operations
	 */

	@Export
	@Override
	public JsBooleanResult catchExceptions(final JsClosure exceptionListener) {
		jsResult.catchExceptions(exceptionListener);
		return this;
	}

	@Export
	@Override
	public JsBooleanResult catchUndefined(final JsClosure undefinedListener) {
		jsResult.catchUndefined(undefinedListener);
		return this;
	}

	@Export
	@Override
	public JsBooleanResult catchUnauthorized(
			final JsClosure unauthorizedListener) {
		jsResult.catchUnauthorized(unauthorizedListener);
		return this;
	}

	@Export
	@Override
	public JsBooleanResult catchImpossible(final JsClosure impossibleListener) {
		jsResult.catchImpossible(impossibleListener);
		return this;
	}

	@Export
	@Override
	public Object get(final Object... params) {
		return jsResult.get(params);
	}

}
