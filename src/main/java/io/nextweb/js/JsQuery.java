package io.nextweb.js;

import io.nextweb.Query;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsObjectCallback;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsQuery implements Exportable {

	private Query query;

	@Export
	public void get(final JsObjectCallback callback) {
		JH.get(query, callback);
	}

	@Export
	public Object get() {
		return JH.get(query);
	}

	@NoExport
	public Query getQuery() {
		return query;
	}

	@NoExport
	public void setQuery(Query query) {
		this.query = query;
	}

	@NoExport
	public static JsQuery wrap(Query query) {
		JsQuery jsQuery = new JsQuery();
		jsQuery.setQuery(query);
		return jsQuery;
	}

	public JsQuery() {
		super();

	}

}
