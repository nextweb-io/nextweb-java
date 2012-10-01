package com.ononedb.nextweb.local.js;

import io.nextweb.js.NextwebJs;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

import com.ononedb.nextweb.local.OnedbLocalNode;

@Export
public class OnedbLocalNodeJs implements Exportable {

	OnedbLocalNode original;

	@Export
	public JsResult shutdown() {
		return JsResult.wrap(original.shutdown(), NextwebJs.getEngine()
				.getEngine().jsFactory().getWrappers());
	}

	@NoExport
	public OnedbLocalNode getOriginal() {
		return original;
	}

	@NoExport
	public void setOriginal(final OnedbLocalNode original) {
		this.original = original;
	}

	@NoExport
	public static OnedbLocalNodeJs wrap(final OnedbLocalNode node) {
		final OnedbLocalNodeJs jsNode = new OnedbLocalNodeJs();

		jsNode.setOriginal(node);

		return jsNode;
	}

	public OnedbLocalNodeJs() {
		super();

	}

}
