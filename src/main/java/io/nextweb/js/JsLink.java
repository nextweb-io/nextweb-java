package io.nextweb.js;

import io.nextweb.Link;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;

@Export
public abstract class JsLink implements Exportable, Link {

	public JsLink() {
		super();
	}

}
