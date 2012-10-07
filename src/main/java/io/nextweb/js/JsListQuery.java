package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.ListQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.JsBooleanResult;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.utils.WrapperCollection;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsListQuery implements JsWrapper<ListQuery>, Exportable,
		JsEntityList<ListQuery, JsListQuery> {

	private ListQuery list;

	public JsListQuery() {
		super();
	}

	@NoExport
	@Override
	public ListQuery getOriginal() {

		return this.list;
	}

	@NoExport
	@Override
	public void setOriginal(final ListQuery original) {
		this.list = original;
	}

	@NoExport
	public static JsListQuery wrap(final ListQuery query) {
		final JsListQuery jsQuery = new JsListQuery();
		jsQuery.setOriginal(query);
		return jsQuery;
	}

	@Export
	@Override
	public JsSession getSession() {
		return JH.jsFactory(list).createSession(list.getSession());
	}

	@Export
	@Override
	public JsExceptionManager getExceptionManager() {

		return JH.jsFactory(list).createExceptionManager(
				list.getExceptionManager());
	}

	/*
	 * List operations
	 */

	@Override
	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
	}

	@Override
	public JsListQuery each(final JsClosure closure) {
		final WrapperCollection wrappers = JH.jsFactory(list).getWrappers();
		this.list.each(JH.wrapJsClosure(closure, wrappers));

		return this;
	}

	@Export
	@Override
	public JsListQuery select(final JsLink propertyType) {
		return JH.jsFactory(list).createListQuery(
				list.select(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsListQuery selectAll(final JsLink propertyType) {
		return JH.jsFactory(list).createListQuery(
				list.selectAll(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsLinkListQuery selectAllLinks() {
		return JH.jsFactory(list).createLinkListQuery(list.selectAllLinks());
	}

	@Export
	@Override
	public JsListQuery selectAll() {
		return JH.jsFactory(list).createListQuery(list.selectAll());
	}

	@Export
	@Override
	public JsBooleanResult has(final Link propertyType) {
		return JsBooleanResult.wrap(list.has(propertyType), list.getSession());
	}

}
