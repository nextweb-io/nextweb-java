package io.nextweb.js.common;

import io.nextweb.Node;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.js.JsNode;
import io.nextweb.js.fn.JsObjectCallback;

import org.timepedia.exporter.client.ExporterUtil;

/**
 * JavaScript helper methods.
 * 
 * @author mroh004
 * 
 */
public class JH {

	public static void get(Result<Node> entityResult,
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

	public static Object get(Result<Node> entityResult) {
		assert entityResult != null;

		if (entityResult.get() == null) {
			return null;
		}
		return ExporterUtil.wrap(JsNode.wrap(entityResult.get()));
	}

}
