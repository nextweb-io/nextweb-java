package io.nextweb.js;

import io.nextweb.Query;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsQuery implements Exportable, JsEntity, JsWrapper<Query> {

	private Query query;

	@Export
	public Object get(Object... params) {
		if (params.length == 0) {
			return ExporterUtil.wrap(JH.getNode(query));
		}

		if (params.length > 1) {
			throw new IllegalArgumentException(
					"Only one argument of type JsClosure is supported.");
		}

		JH.getNode(query, JH.asJsClosure((JavaScriptObject) params[0], JH
				.jsFactory(query).getWrappers()));

		return ExporterUtil.wrap(JH.jsFactory(query).createQuery(query));
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
