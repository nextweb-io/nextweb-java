package io.nextweb.js;

import io.nextweb.Query;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsQuery implements Exportable, JsEntity, JsWrapper<Query> {

	private Query query;

	@Export
	public void get(final JsClosure callback) {
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

	@Export
	@Override
	public JsSession getSession() {
		return JH.jsFactory(query).createSession(query.getSession());
	}

	@Export
	@Override
	public JsExceptionManager getExceptionManager() {
		return JH.jsFactory(query).createExceptionManager(
				query.getExceptionManager());
	}

	@Export
	@Override
	public void catchExceptions(final JsClosure listener) {
		query.getExceptionManager().catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				listener.apply(t);
			}
		});
	}

}
