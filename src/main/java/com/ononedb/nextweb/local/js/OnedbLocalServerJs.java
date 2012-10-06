package com.ononedb.nextweb.local.js;

import io.nextweb.js.NextwebJs;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

import com.ononedb.nextweb.local.OnedbLocalServer;

@Export
public class OnedbLocalServerJs implements Exportable {

	OnedbLocalServer original;

	@Export
	public JsResult shutdown() {
		return JsResult.wrap(original.shutdown(), NextwebJs.getEngine()
				.getEngine().jsFactory().getWrappers());
	}

	@NoExport
	public OnedbLocalServer getOriginal() {
		return original;
	}

	@NoExport
	public void setOriginal(final OnedbLocalServer original) {
		this.original = original;
	}

	@NoExport
	public static OnedbLocalServerJs wrap(final OnedbLocalServer node) {
		final OnedbLocalServerJs jsNode = new OnedbLocalServerJs();

		jsNode.setOriginal(node);

		return jsNode;
	}

	public OnedbLocalServerJs() {
		super();

	}

}
