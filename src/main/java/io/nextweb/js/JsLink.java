package io.nextweb.js;

import io.nextweb.Link;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLink implements Exportable {

	private Link link;

	@Export
	public String getUri() {
		return link.getUri();
	}

	@Export
	public String uri() {
		return link.uri();
	}

	@NoExport
	public void setLink(Link link) {
		this.link = link;
	}

	@NoExport
	public Link getLink() {
		return this.link;
	}

	public JsLink() {
		super();
	}

}
