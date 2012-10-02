package io.nextweb.js.fn;

import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.js.common.JH;
import io.nextweb.js.utils.WrapperCollection;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsResult implements Exportable {

	Result<Object> result;
	WrapperCollection wrappers;

	@Export
	public Object get(final Object... params) {

		if (params.length == 0) {
			return performGet();
		}

		if (params.length > 1) {
			throw new IllegalArgumentException(
					"Only one argument of type JsClosure is supported.");
		}

		performGet(JH.asJsClosure((JavaScriptObject) params[0], wrappers));

		return ExporterUtil.wrap(this);
	}

	@NoExport
	private final Object performGet() {
		Object node = result.get();

		if (node != null) {
			node = wrappers.createJsEngineWrapper(node);
			node = wrappers.wrapValueObjectForJs(node);
		}

		return node;
	}

	@NoExport
	private final void performGet(final JsClosure onSuccess) {
		result.get(new Closure<Object>() {

			@Override
			public void apply(final Object o) {

				if (o instanceof JavaScriptObject) {
					onSuccess.apply(o);
					return;
				}

				final Object wrappedEngineNode = wrappers
						.createJsEngineWrapper(o);

				onSuccess.apply(ExporterUtil.wrap(wrappedEngineNode));
			}

		});

	}

	@NoExport
	public Result<?> getResult() {
		return result;
	}

	@NoExport
	public void setResult(final Result<Object> result) {
		this.result = result;
	}

	@NoExport
	public WrapperCollection getWrappers() {
		return wrappers;
	}

	@NoExport
	public void setWrappers(final WrapperCollection wrappers) {
		this.wrappers = wrappers;
	}

	public JsResult() {
		super();
	}

	@SuppressWarnings("unchecked")
	@NoExport
	public static JsResult wrap(final Result<?> result,
			final WrapperCollection wrappers) {
		final JsResult jsResult = new JsResult();
		jsResult.setResult((Result<Object>) result);
		jsResult.setWrappers(wrappers);
		return jsResult;
	}

}
