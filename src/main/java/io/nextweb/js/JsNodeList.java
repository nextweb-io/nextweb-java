package io.nextweb.js;

import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.fn.Closure;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;
import com.ononedb.nextweb.common.H;

@Export
public class JsNodeList implements Exportable, JsWrapper<NodeList> {

	private NodeList list;

	@Export
	public JavaScriptObject nodes() {

	}

	@Export
	public JavaScriptObject values() {
		also test catch exceptions for JavaScript?!?!
	}

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

}
