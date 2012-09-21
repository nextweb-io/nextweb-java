package io.nextweb.js;

import io.nextweb.LinkListQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsLinkListQuery implements Exportable, JsWrapper<LinkListQuery>,
		JsEntityList {

	private LinkListQuery listQuery;

	@Export
	public void get(final JsClosure callback) {
		JH.getLinkList(listQuery, callback);
	}

	@Export
	public Object get() {
		return JH.getLinkList(listQuery);
	}

	@NoExport
	@Override
	public LinkListQuery getOriginal() {
		return listQuery;
	}

	@NoExport
	@Override
	public void setOriginal(LinkListQuery original) {
		this.listQuery = original;
	}

	@NoExport
	public static JsLinkListQuery wrap(LinkListQuery listQuery) {
		JsLinkListQuery jsListQuery = new JsLinkListQuery();
		jsListQuery.setOriginal(listQuery);
		return jsListQuery;
	}

	public JsLinkListQuery() {
		super();

	}

	@Export
	@Override
	public JsSession getSession() {

		return JH.jsFactory(listQuery).createSession(listQuery.getSession());
	}

	@Export
	@Override
	public JsExceptionManager getExceptionManager() {

		return JH.jsFactory(listQuery).createExceptionManager(
				listQuery.getExceptionManager());
	}

}
