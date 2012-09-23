package io.nextweb.js.fn;

import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsResult implements Exportable {

	Result<Object> result;

	@Export
	public Object get() {
		return result.get();
	}

	@Export
	public void get(final JsClosure onSuccess) {
		result.get(new Closure<Object>() {

			@Override
			public void apply(Object o) {
				onSuccess.apply(o);
			}

		});

	}

	@NoExport
	public Result<?> getResult() {
		return result;
	}

	@NoExport
	public void setResult(Result<Object> result) {
		this.result = result;
	}

	public JsResult() {
		super();
	}

	@SuppressWarnings("unchecked")
	@NoExport
	public static JsResult wrap(Result<?> result) {
		JsResult jsResult = new JsResult();
		jsResult.setResult((Result<Object>) result);
		return jsResult;
	}

}
