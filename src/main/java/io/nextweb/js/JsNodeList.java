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
		JsEntityList {

	private NodeList list;

	@Export
	public JavaScriptObject[] nodes() {
		JavaScriptObject[] result = new JavaScriptObject[list.size()];
		int count = 0;
		for (Node n : list) {
			result[count] = ExporterUtil.wrap(JH.jsFactory(list).createNode(n));
		}
		return result;
	}

	@Export
	public JavaScriptObject values() {
		JavaScriptObject[] result = new JavaScriptObject[list.size()];
		int count = 0;
		for (Node n : list) {
			result[count] = JH.forceWrapIntoJavaScriptObject((JH
					.jsFactory(list).wrapValueObjectForJs(n.getValue())));
			count++;
		}

		JSONArray ar = new JSONArray(unwrapBasicTypes(ExporterUtil.wrap(JsArray
				.wrap(result))));

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
		H.each(list, new Closure<Node>() {

			@Override
			public void apply(Node o) {
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
	public void setOriginal(NodeList original) {
		this.list = original;
	}

	@NoExport
	public static JsNodeList wrap(NodeList list) {
		JsNodeList jsList = new JsNodeList();
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
