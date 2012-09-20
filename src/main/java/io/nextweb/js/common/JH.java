package io.nextweb.js.common;

import io.nextweb.Node;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.js.JsNode;
import io.nextweb.js.fn.JsObjectCallback;
import io.nextweb.js.utils.WrapperCollection;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * JavaScript helper methods.
 * 
 * @author mroh004
 * 
 */
public class JH {

	public static final void get(Result<Node> entityResult,
			final JsObjectCallback callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(new ResultCallback<Node>() {

			@Override
			public void onSuccess(Node result) {
				callback.run(ExporterUtil.wrap(JsNode.wrap(result)));
			}
		});
	}

	public static final Object get(Result<Node> entityResult) {
		assert entityResult != null;

		if (entityResult.get() == null) {
			return null;
		}
		return ExporterUtil.wrap(JsNode.wrap(entityResult.get()));
	}

	public static final void triggerCallback(JavaScriptObject fn,
			WrapperCollection wrappers, Object[] params) {
		triggerCallbackJs(fn,
				ExporterUtil.wrap(JsArray.wrap(toJsoArray(params, wrappers))));
	}

	public static final JavaScriptObject[] toJsoArray(Object[] array,
			WrapperCollection wrappers) {
		JavaScriptObject[] result = new JavaScriptObject[array.length];
		for (int i = 0; i <= array.length - 1; i++) {
			Object rawWrapped = wrappers.wrapForJs(wrappers
					.createJsEngineWrapper(array[i]));
			if (rawWrapped instanceof JavaScriptObject) {
				result[i] = (JavaScriptObject) rawWrapped;
			} else {
				result[i] = ExporterUtil.wrap(rawWrapped);
			}
		}
		return result;
	}

	public static final native void triggerCallbackJs(JavaScriptObject fn,
			JavaScriptObject jsArray)/*-{
										fn.apply(this, jsArray.getArray());
										}-*/;

}
