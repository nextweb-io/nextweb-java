package io.nextweb.js;

import io.nextweb.Node;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNode implements Exportable {

	private Node node;

	@Export
	public String getUri() {
		return node.getUri();
	}

	@Export
	public String uri() {
		return node.uri();
	}

	@NoExport
	public Node getNode() {
		return node;
	}

	@NoExport
	public void setNode(Node node) {
		this.node = node;
	}

	public JsNode() {
		super();
	}

}
