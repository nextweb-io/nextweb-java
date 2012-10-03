package io.nextweb.js.fn;

import io.nextweb.fn.BasicResult;
import io.nextweb.fn.Closure;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.operations.JsExceptionListeners;
import io.nextweb.js.utils.WrapperCollection;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsResult implements Exportable, JsExceptionListeners<JsResult> {

	BasicResult<Object> result;
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

	@Export
	@Override
	public JsResult catchExceptions(final JsClosure exceptionListener) {
		exceptionManager().catchExceptions(exceptionListener);
		return this;
	}

	@NoExport
	private final JsExceptionManager exceptionManager() {
		return JsExceptionManager.wrap(result.getExceptionManager());
	}

	@Export
	@Override
	public JsResult catchUndefined(final JsClosure undefinedListener) {
		exceptionManager().catchUndefined(undefinedListener);
		return this;
	}

	@Export
	@Override
	public JsResult catchUnauthorized(final JsClosure unauthorizedListener) {
		exceptionManager().catchUnauthorized(unauthorizedListener);
		return this;
	}

	@Export
	@Override
	public JsResult catchImpossible(final JsClosure impossibleListener) {
		exceptionManager().catchImpossible(impossibleListener);
		return this;
	}

	@NoExport
	private final Object performGet() {
		Object node = result.get();

		if (node != null) {
			node = wrappers.createJsEngineWrapper(node);
			node = wrappers.convertValueObjectForJs(node);
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

				onSuccess.apply(wrappers
						.convertValueObjectForJs((wrappedEngineNode)));
			}

		});

	}

	@NoExport
	public BasicResult<?> getResult() {
		return result;
	}

	@NoExport
	public void setResult(final BasicResult<Object> result) {
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
	public static JsResult wrap(final BasicResult<?> result,
			final WrapperCollection wrappers) {
		final JsResult jsResult = new JsResult();
		jsResult.setResult((BasicResult<Object>) result);
		jsResult.setWrappers(wrappers);
		return jsResult;
	}

}
