package com.ononedb.nextweb.local.js;

import io.nextweb.js.NextwebJs;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

import com.ononedb.nextweb.local.OnedbLocalDb;

@Export
public class OnedbLocalNodeJs implements Exportable {

	OnedbLocalDb original;

	@Export
	public JsResult shutdown() {
		return JsResult.wrap(original.shutdown(), NextwebJs.getEngine()
				.getEngine().jsFactory().getWrappers());
	}

	@NoExport
	public OnedbLocalDb getOriginal() {
		return original;
	}

	@NoExport
	public void setOriginal(final OnedbLocalDb original) {
		this.original = original;
	}

	@NoExport
	public static OnedbLocalNodeJs wrap(final OnedbLocalDb node) {
		final OnedbLocalNodeJs jsNode = new OnedbLocalNodeJs();

		jsNode.setOriginal(node);

		return jsNode;
	}

	public OnedbLocalNodeJs() {
		super();

	}

}
