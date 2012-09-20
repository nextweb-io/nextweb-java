package io.nextweb.js;

import io.nextweb.Query;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsObjectCallback;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsQuery implements Exportable, JsWrapper<Query> {

	private Query query;

	@Export
	public void get(final JsObjectCallback callback) {
		JH.get(query, callback);
	}

	@Export
	public Object get() {
		return JH.get(query);
	}

	@Override
	@NoExport
	public Query getOriginal() {
		return query;
	}

	@Override
	@NoExport
	public void setOriginal(Query query) {
		this.query = query;
	}

	@NoExport
	public static JsQuery wrap(Query query) {
		JsQuery jsQuery = new JsQuery();
		jsQuery.setOriginal(query);
		return jsQuery;
	}

	public JsQuery() {
		super();

	}

}
