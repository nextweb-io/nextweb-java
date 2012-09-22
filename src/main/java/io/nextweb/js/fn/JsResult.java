package io.nextweb.js.fn;

import io.nextweb.fn.RequestCallbackImpl;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;

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
	public void get(final JsClosure onSuccess, final JsClosure onFailure) {
		result.get(new RequestCallbackImpl(null, null) {

			@Override
			public void onSuccess(Object result) {
				onSuccess.apply(result);
			}

			@Override
			public void onUnauthorized(Object origin,
					AuthorizationExceptionResult r) {
				onFailure.apply(new Exception("Insufficient authorization: "
						+ r.getMessage()));
			}

			@Override
			public void onUndefined(Object origin, String message) {
				onFailure.apply(new Exception("Node not defined: " + message));
			}

			@Override
			public void onFailure(Object origin, Throwable t) {
				onFailure.apply(t);
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
