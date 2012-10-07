package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.JsBooleanResult;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.operations.entity.impl.JsOpCommon;
import io.nextweb.js.utils.WrapperCollection;

import java.util.ArrayList;
import java.util.List;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLinkList implements Exportable, JsWrapper<LinkList>,
		JsEntityList<LinkList, JsLinkList> {

	private LinkList list;

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

	public static JsLinkList wrap(final LinkList list) {
		final JsLinkList jslist = new JsLinkList();
		jslist.setOriginal(list);
		return jslist;
	}

	public JsLinkList() {
		super();

	}

	/*
	 * specific operations
	 */

	@Export
	public String[] uris() {
		final List<String> uris = new ArrayList<String>(list.size());

		for (final Link l : list) {
			uris.add(l.uri());
		}

		return uris.toArray(new String[] {});
	}

	/*
	 * common getters
	 */

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

	@Export
	@Override
	public JsLinkList each(final JsClosure closure) {
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

	@Export
	@Override
	public JsListQuery setValue(final Object newValue) {
		return JH.jsFactory(list).createListQuery(
				list.setValue(JsOpCommon.getJavaValue(list, newValue)));
	}

	@Export
	@Override
	public JsListQuery setValueSafe(final Object newValue) {
		return JH.jsFactory(list).createListQuery(
				list.setValueSafe(JsOpCommon.getJavaValue(list, newValue)));
	}

}
