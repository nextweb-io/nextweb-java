package io.nextweb.js;

import io.nextweb.LinkListQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsLinkListQuery implements Exportable, JsWrapper<LinkListQuery> {

	private LinkListQuery listQuery;

	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
	}

	@NoExport
	@Override
	public LinkListQuery getOriginal() {
		return listQuery;
	}

	@NoExport
	@Override
	public void setOriginal(final LinkListQuery original) {
		this.listQuery = original;
	}

	@NoExport
	public static JsLinkListQuery wrap(final LinkListQuery listQuery) {
		final JsLinkListQuery jsListQuery = new JsLinkListQuery();
		jsListQuery.setOriginal(listQuery);
		return jsListQuery;
	}

	public JsLinkListQuery() {
		super();

	}

	@Export
	public JsSession getSession() {

		return JH.jsFactory(listQuery).createSession(listQuery.getSession());
	}

	@Export
	public JsExceptionManager getExceptionManager() {

		return JH.jsFactory(listQuery).createExceptionManager(
				listQuery.getExceptionManager());
	}

}
