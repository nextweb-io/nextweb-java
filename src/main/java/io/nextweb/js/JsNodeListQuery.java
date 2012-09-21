package io.nextweb.js;

import io.nextweb.NodeListQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNodeListQuery implements JsWrapper<NodeListQuery>, Exportable,
		JsEntityList {

	private NodeListQuery list;

	public JsNodeListQuery() {
		super();

	}

	@Export
	public void get(final JsClosure callback) {
		JH.getNodeList(list, callback);
	}

	@Export
	public Object get() {
		return JH.getNodeList(list);
	}

	@NoExport
	@Override
	public NodeListQuery getOriginal() {

		return this.list;
	}

	@NoExport
	@Override
	public void setOriginal(NodeListQuery original) {
		this.list = original;
	}

	public static JsNodeListQuery wrap(NodeListQuery query) {
		final JsNodeListQuery jsQuery = new JsNodeListQuery();
		jsQuery.setOriginal(query);
		return jsQuery;
	}

	@Export
	@Override
	public JsSession getSession() {
		return JH.jsFactory(list).createSession(list.getSession());
	}

	@Export
	@Override
	public JsExceptionManager getExceptionManager() {

		return JH.jsFactory(list).createExceptionManager(
				list.getExceptionManager());
	}

}
