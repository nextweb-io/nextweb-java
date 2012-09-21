package io.nextweb.js;

import io.nextweb.NodeListQuery;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNodeListQuery implements JsWrapper<NodeListQuery>, Exportable {

	private NodeListQuery nodeListQuery;

	public JsNodeListQuery() {
		super();

	}

	@NoExport
	@Override
	public NodeListQuery getOriginal() {

		return this.nodeListQuery;
	}

	@NoExport
	@Override
	public void setOriginal(NodeListQuery original) {
		this.nodeListQuery = original;
	}

	public static JsNodeListQuery wrap(NodeListQuery query) {
		final JsNodeListQuery jsQuery = new JsNodeListQuery();
		jsQuery.setOriginal(query);
		return jsQuery;
	}

}
