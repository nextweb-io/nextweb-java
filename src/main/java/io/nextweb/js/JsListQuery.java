package io.nextweb.js;

import io.nextweb.LinkListQuery;

import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsListQuery implements Exportable, JsWrapper<LinkListQuery> {

	private LinkListQuery listQuery;

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
	public static JsListQuery wrap(LinkListQuery listQuery) {
		JsListQuery jsListQuery = new JsListQuery();
		jsListQuery.setOriginal(listQuery);
		return jsListQuery;
	}

	public JsListQuery() {
		super();

	}

}
