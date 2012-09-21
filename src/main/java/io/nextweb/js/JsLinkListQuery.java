package io.nextweb.js;

import io.nextweb.LinkListQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsObjectCallback;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsLinkListQuery implements Exportable, JsWrapper<LinkListQuery> {

	private LinkListQuery listQuery;

	@Export
	public void get(final JsObjectCallback callback) {
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

}
