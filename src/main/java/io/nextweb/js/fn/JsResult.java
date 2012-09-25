package io.nextweb.js.fn;

import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.js.utils.WrapperCollection;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsResult implements Exportable {

	Result<Object> result;
	WrapperCollection wrappers;

	@Export
	public Object get() {
		Object node = result.get();

		if (node != null) {
			node = wrappers.createJsEngineWrapper(node);
			node = wrappers.wrapValueObjectForJs(node);
		}

		return node;
	}

	@Export
	public void get(final JsClosure onSuccess) {
		result.get(new Closure<Object>() {

			@Override
			public void apply(Object o) {
				// GWT.log("got async: " + o);
				Object wrappedEngineNode = wrappers.createJsEngineWrapper(o);
				// GWT.log("wrapped ASYNC engine node: " + wrappedEngineNode);
				onSuccess.apply(wrappedEngineNode);
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

	@NoExport
	public WrapperCollection getWrappers() {
		return wrappers;
	}

	@NoExport
	public void setWrappers(WrapperCollection wrappers) {
		this.wrappers = wrappers;
	}

	public JsResult() {
		super();
	}

	@SuppressWarnings("unchecked")
	@NoExport
	public static JsResult wrap(Result<?> result, WrapperCollection wrappers) {
		JsResult jsResult = new JsResult();
		jsResult.setResult((Result<Object>) result);
		jsResult.setWrappers(wrappers);
		return jsResult;
	}

}
