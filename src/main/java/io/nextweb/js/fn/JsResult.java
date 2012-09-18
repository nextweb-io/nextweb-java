package io.nextweb.js.fn;

import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsResult implements Exportable {

	Result<?> result;

	@Export
	public Object get() {
		return result.get();
	}

	@Export
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void get(final JsObjectCallback onSuccess,
			final JsObjectCallback onFailure) {
		result.get(new ResultCallback() {

			@Override
			public void onSuccess(Object result) {
				onSuccess.run(result);
			}

			@Override
			public void onFailure(Throwable t) {
				onFailure.run(t);
			}

		});
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
