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
public class JsQuery implements Exportable, JsEntity<Query> {

	private Query query;

	@Override
	@Export
	public Object get(Object... params) {
		return JH.get(this, params);
	}

	@Override
	public Object append(Object value) {
		return JH.op(query).append().append(value);
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

	@Export
	@Override
	public JsLinkListQuery selectAllLinks() {
		return JH.jsFactory(query).createLinkListQuery(query.selectAllLinks());
	}

	@Export
	@Override
	public JsNodeListQuery selectAll(JsLink propertyType) {
		return JH.jsFactory(query).createNodeListQuery(
				query.selectAll(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsQuery select(JsLink propertyType) {
		return JH.jsFactory(query).createQuery(
				query.select(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsNodeListQuery selectAll() {
		return JH.jsFactory(query).createNodeListQuery(query.selectAll());
	}

}
