package io.nextweb.js.engine;

import io.nextweb.Link;
import io.nextweb.js.JsLink;
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
	public JsLink createLink(Link link) {
		JsLink jsLink = JsLink.wrap(link);
		applyAllPlugins(entityPlugins, jsLink);
		return jsLink;
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
