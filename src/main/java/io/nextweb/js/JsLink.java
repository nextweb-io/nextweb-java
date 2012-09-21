package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLink implements Exportable, JsWrapper<Link> {

	private Link link;

	@Export
	public JsNodeListQuery selectAll() {
		return JH.jsFactory(link).createNodeListQuery(link.selectAll());
	}

	@Export
	public void get(final JsClosure callback) {
		JH.get(link, callback);
	}

	@Export
	public Object get() {
		return JH.get(link);
	}

	@Export
	public JsQuery select(JsLink propertyType) {
		return JH.jsFactory(propertyType.getOriginal()).createQuery(
				link.select(propertyType.getOriginal()));

	}

	@Export
	public String getUri() {
		return link.getUri();
	}

	@Export
	public String uri() {
		return link.uri();
	}

	@Override
	@NoExport
	public void setOriginal(Link link) {
		this.link = link;
	}

	@Override
	@NoExport
	public Link getOriginal() {
		return this.link;
	}

	public JsLink() {
		super();
	}

	@NoExport
	public static JsLink wrap(Link link) {
		JsLink jsLink = new JsLink();
		jsLink.setOriginal(link);
		return jsLink;
	}

}
