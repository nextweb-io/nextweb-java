package io.nextweb.js.plugins;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsPluginUtils implements Exportable {

	public final static native void plugin(JavaScriptObject dest,
			JavaScriptObject pluginFactory)/*-{

		if (!pluginFactory.create) {
			throw "PluginFactory must implement function create";
		}

		var plugin = pluginFactory.create(dest);

		for (obj in plugin) {
			if (typeof plugin[obj] != "function") {
				continue;
			}
			dest[obj] = plugin[obj];
		}

	}-*/;

	public JsPluginUtils() {
		super();
	}

}
