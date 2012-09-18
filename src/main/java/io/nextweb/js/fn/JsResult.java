package io.nextweb.js.fn;

import io.nextweb.fn.Result;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsResult implements Exportable {

	Result<?> result;

	@Export
	public Object get() {
		result.
	}

	@NoExport
	public Result<?> getResult() {
		return result;
	}

	@NoExport
	public void setResult(Result<?> result) {
		this.result = result;
	}

	public JsResult() {
		super();
	}

	@NoExport
	public static JsResult wrap(Result<?> result) {
		JsResult jsResult = new JsResult();
		jsResult.setResult(result);
		return jsResult;
	}

}
