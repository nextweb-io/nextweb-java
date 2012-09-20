package io.nextweb.js;

import io.nextweb.ListQuery;

import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsListQuery implements Exportable, JsWrapper<ListQuery> {

	private ListQuery listQuery;

	@NoExport
	@Override
	public ListQuery getOriginal() {
		return listQuery;
	}

	@NoExport
	@Override
	public void setOriginal(ListQuery original) {
		this.listQuery = original;
	}

	@NoExport
	public static JsListQuery wrap(ListQuery listQuery) {
		JsListQuery jsListQuery = new JsListQuery();
		jsListQuery.setOriginal(listQuery);
		return jsListQuery;.
	}

	public JsListQuery() {
		super();

	}

}
