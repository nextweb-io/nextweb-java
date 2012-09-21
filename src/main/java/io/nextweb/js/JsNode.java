package io.nextweb.js;

import io.nextweb.Node;
import io.nextweb.js.engine.NextwebEngineJs;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNode implements Exportable, JsWrapper<Node> {

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
		return ((NextwebEngineJs) node.getSession().getEngine()).jsFactory()
				.wrapForJs(node.getValue());
	}

	@Export
	public Object value() {
		return getValue();
	}

	@Override
	@NoExport
	public Node getOriginal() {
		return node;
	}

	@Override
	@NoExport
	public void setOriginal(Node node) {
		this.node = node;
	}

	@NoExport
	public static JsNode wrap(Node node) {
		JsNode jsNode = new JsNode();
		jsNode.setOriginal(node);
		return jsNode;
	}

	public JsNode() {
		super();
	}

}
