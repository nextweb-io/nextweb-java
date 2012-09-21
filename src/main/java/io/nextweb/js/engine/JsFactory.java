package io.nextweb.js.engine;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.js.JsLink;
import io.nextweb.js.JsLinkList;
import io.nextweb.js.JsLinkListQuery;
import io.nextweb.js.JsNode;
import io.nextweb.js.JsNodeList;
import io.nextweb.js.JsNodeListQuery;
import io.nextweb.js.JsQuery;
import io.nextweb.js.JsSession;
import io.nextweb.js.plugins.JsPluginUtils;
import io.nextweb.js.utils.WrapperCollection;

import java.util.ArrayList;
import java.util.List;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

public class JsFactory implements Exportable {

	private final List<JavaScriptObject> entityPlugins;
	private final WrapperCollection wrapperCollection;

	@Export
	public void registerEntityPlugin(JavaScriptObject plugin) {

		this.entityPlugins.add(plugin);
	}

	@NoExport
	private final void applyAllPlugins(List<JavaScriptObject> pluginFactories,
			Exportable dest) {

		for (JavaScriptObject pluginFactory : pluginFactories) {
			JsPluginUtils.plugin(ExporterUtil.wrap(dest), pluginFactory);
		}
	}

	@NoExport
	public JsQuery createQuery(Query query) {
		final JsQuery jsQuery = JsQuery.wrap(query);
		applyAllPlugins(entityPlugins, jsQuery);
		return jsQuery;
	}

	@NoExport
	public JsLink createLink(Link link) {
		final JsLink jsLink = JsLink.wrap(link);
		applyAllPlugins(entityPlugins, jsLink);
		return jsLink;
	}

	@NoExport
	public JsNode createNode(Node node) {
		final JsNode jsNode = JsNode.wrap(node);
		applyAllPlugins(entityPlugins, jsNode);
		return jsNode;
	}

	@NoExport
	public JsSession createSession(Session session) {
		final JsSession jsSession = JsSession.wrap(session);

		return jsSession;
	}

	@NoExport
	public JsNodeList createNodeList(NodeList nodeList) {
		final JsNodeList jsNodeList = JsNodeList.wrap(nodeList);

		return jsNodeList;
	}

	@NoExport
	public JsLinkList createLinkList(LinkList linkList) {
		final JsLinkList jsList = JsLinkList.wrap(linkList);

		return jsList;
	}

	@NoExport
	public JsLinkListQuery createLinkListQuery(LinkListQuery listQuery) {
		final JsLinkListQuery jsListQuery = JsLinkListQuery.wrap(listQuery);

		return jsListQuery;
	}

	@NoExport
	public JsNodeListQuery createNodeListQuery(NodeListQuery listQuery) {
		final JsNodeListQuery jsListQuery = JsNodeListQuery.wrap(listQuery);

		return jsListQuery;
	}

	@NoExport
	public Object wrapForJs(Object in) {
		return this.wrapperCollection.wrapForJs(in);
	}

	@NoExport
	public WrapperCollection getWrappers() {
		return this.wrapperCollection;
	}

	public JsFactory() {
		super();
		this.entityPlugins = new ArrayList<JavaScriptObject>();
		this.wrapperCollection = new WrapperCollection(this);
	}

}
