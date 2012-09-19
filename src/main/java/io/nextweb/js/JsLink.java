package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.fn.ResultCallback;
import io.nextweb.js.fn.JsObjectCallback;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLink implements Exportable {

	private Link link;

	@Export
	public void get(final JsObjectCallback callback) {
		assert link != null;

		link.get(new ResultCallback<Node>() {

			@Override
			public void onSuccess(Node result) {
				callback.run(result);
			}
		});
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
