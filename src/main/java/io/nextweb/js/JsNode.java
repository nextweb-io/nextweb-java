package io.nextweb.js;

import io.nextweb.Node;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNode implements Exportable, JsEntity, JsWrapper<Node> {

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
				.wrapValueObjectForJs(node.getValue());
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

	@Export
	@Override
	public JsSession getSession() {
		return JH.jsFactory(node).createSession(node.getSession());
	}

	@Export
	@Override
	public JsExceptionManager getExceptionManager() {
		return JH.jsFactory(node).createExceptionManager(
				node.getExceptionManager());
	}

	@Export
	@Override
	public void catchExceptions(final JsClosure listener) {
		node.getExceptionManager().catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				listener.apply(t);
			}
		});
	}

	@Export
	@Override
	public JsLinkListQuery selectAllLinks() {
		return JH.jsFactory(node).createLinkListQuery(node.selectAllLinks());
	}

	@Export
	@Override
	public JsNodeListQuery selectAll(JsLink propertyType) {
		return JH.jsFactory(node).createNodeListQuery(
				node.selectAll(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsQuery select(JsLink propertyType) {
		return JH.jsFactory(node).createQuery(
				node.select(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsNodeListQuery selectAll() {
		return JH.jsFactory(node).createNodeListQuery(node.selectAll());
	}

}
