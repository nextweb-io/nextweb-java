package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;

import java.util.ArrayList;
import java.util.List;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLinkList implements Exportable, JsWrapper<LinkList>,
		JsEntityList<LinkList> {

	private LinkList list;

	@Override
	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
	}

	@NoExport
	@Override
	public LinkList getOriginal() {

		return list;
	}

	@NoExport
	@Override
	public void setOriginal(final LinkList original) {
		this.list = original;
	}

	@Export
	public String[] uris() {
		final List<String> uris = new ArrayList<String>(list.size());

		for (final Link l : list) {
			uris.add(l.uri());
		}

		return uris.toArray(new String[] {});
	}

	public static JsLinkList wrap(final LinkList list) {
		final JsLinkList jslist = new JsLinkList();
		jslist.setOriginal(list);
		return jslist;
	}

	public JsLinkList() {
		super();

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
