package io.nextweb.js;

import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.fn.Closure;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.JsArray;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.ononedb.nextweb.common.H;

@Export
public class JsNodeList implements Exportable, JsWrapper<NodeList>,
		JsEntityList<NodeList> {

	private NodeList list;

	@Override
	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
	}

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

	@Export
	public int size() {
		return list.size();

	}

	@Export
	public void each(final JsClosure closure) {
		H.each(this.list, list, new Closure<Node>() {

			@Override
			public void apply(final Node o) {
				closure.apply(ExporterUtil.wrap(JH.jsFactory(list)
						.createNode(o)));
			}
		});
	}

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
