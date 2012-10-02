package io.nextweb.js;

import io.nextweb.ListQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNodeListQuery implements JsWrapper<ListQuery>, Exportable,
		JsEntityList<ListQuery> {

	private ListQuery list;

	public JsNodeListQuery() {
		super();
	}

	@Override
	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
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
	public static JsNodeListQuery wrap(final ListQuery query) {
		final JsNodeListQuery jsQuery = new JsNodeListQuery();
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

}
