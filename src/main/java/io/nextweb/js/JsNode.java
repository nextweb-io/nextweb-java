package io.nextweb.js;

import io.nextweb.Node;
import io.nextweb.js.engine.NextwebEngineJs;

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
		return getUri();
	}

	@Export
	public Object getValue() {
		return ((NextwebEngineJs) node.getSession().getEngine()).wrapForJs(node
				.getValue());
	}

	@Export
	public Object value() {
		return getValue();
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
