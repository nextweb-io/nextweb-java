package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.JsArray;
import io.nextweb.js.common.JsBooleanResult;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.operations.entity.impl.JsOpCommon;
import io.nextweb.js.utils.WrapperCollection;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

@Export
public class JsNodeList implements Exportable, JsWrapper<NodeList>,
		JsEntityList<NodeList, JsNodeList> {

	private NodeList list;

	private final native JavaScriptObject unwrapBasicTypes(
			JavaScriptObject jsArray)/*-{
										var values = jsArray.getArray();

										for ( var i = 0; i <= values.length - 1; i++) {
										var value = values[i];
										if (value.isJsBasicType
										&& typeof value.isJsBasicType === 'function') {
										var rpl;
										if (value.isString() != 0) {
										rpl = value.stringValue();
										}

										if (value.isInt() != 0) {
										rpl = value.intValue();
										}

										if (value.isDouble() != 0) {
										rpl = value.doubleValue();
										}

										if (value.isBoolean() != 0) {
										rpl = value.booleanValue().value;
										}
										values[i] = rpl;
										}
										}

										return values;
										}-*/;

	@NoExport
	@Override
	public NodeList getOriginal() {
		return list;
	}

	@NoExport
	@Override
	public void setOriginal(final NodeList original) {
		this.list = original;
	}

	@NoExport
	public static JsNodeList wrap(final NodeList list) {
		final JsNodeList jsList = new JsNodeList();
		jsList.setOriginal(list);
		return jsList;
	}

	public JsNodeList() {
		super();

	}

	/*
	 * Js interface
	 */

	@Export
	public JavaScriptObject[] nodes() {
		final JavaScriptObject[] result = new JavaScriptObject[list.size()];
		final int count = 0;
		for (final Node n : list) {
			result[count] = ExporterUtil.wrap(JH.jsFactory(list).createNode(n));
		}
		return result;
	}

	@Export
	public JavaScriptObject values() {
		final JavaScriptObject[] result = new JavaScriptObject[list.size()];
		int count = 0;
		for (final Node n : list) {
			result[count] = JH.forceWrapIntoJavaScriptObject((JH
					.jsFactory(list).wrapValueObjectForJs(n.getValue())));
			count++;
		}

		final JSONArray ar = new JSONArray(
				unwrapBasicTypes(ExporterUtil.wrap(JsArray.wrap(result))));

		return ar.getJavaScriptObject();
	}

	@Export
	public int size() {
		return list.size();

	}

	@Override
	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
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

	@Override
	@Export
	public JsNodeList each(final JsClosure closure) {

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
