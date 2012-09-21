package io.nextweb.js;

import io.nextweb.NodeList;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNodeList implements Exportable, JsWrapper<NodeList> {

	private NodeList list;

	@NoExport
	@Override
	public NodeList getOriginal() {
		return list;
	}

	@NoExport
	@Override
	public void setOriginal(NodeList original) {
		this.list = original;
	}

	public static JsNodeList wrap(NodeList list) {
		JsNodeList jsList = new JsNodeList();
		jsList.setOriginal(list);
		return jsList;
	}

	public JsNodeList() {
		super();

	}

}
