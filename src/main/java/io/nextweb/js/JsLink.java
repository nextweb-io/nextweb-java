package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsObjectCallback;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLink implements Exportable {

	private Link link;

	@Export
	public void get(final JsObjectCallback callback) {
		JH.get(link, callback);
	}

	@Export
	public Object get() {
		return JH.get(link);
	}

	@Export
	public JsQuery select(JsLink propertyType) {
		return JsQuery.wrap(link.select(propertyType.getLink()));
	}

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

	@NoExport
	public static JsLink wrap(Link link) {
		JsLink jsLink = new JsLink();
		jsLink.setLink(link);
		return jsLink;
	}

}
