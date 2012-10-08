package io.nextweb.js.common;

import io.nextweb.common.LocalServer;
import io.nextweb.js.NextwebJs;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLocalServer implements Exportable {

	LocalServer original;

	@Export
	public JsResult shutdown() {
		return JsResult.wrap(original.shutdown(), NextwebJs.getEngine()
				.getEngine().jsFactory().getWrappers());
	}

	@NoExport
	public LocalServer getOriginal() {
		return original;
	}

	@NoExport
	public void setOriginal(final LocalServer original) {
		this.original = original;
	}

	@NoExport
	public static JsLocalServer wrap(final LocalServer node) {
		final JsLocalServer jsNode = new JsLocalServer();

		jsNode.setOriginal(node);

		return jsNode;
	}

	public JsLocalServer() {
		super();

	}

}
