package io.nextweb.js.plugins;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsPluginUtils implements Exportable {

	public final static native void plugin(JavaScriptObject dest,
			JavaScriptObject pluginFactory)/*-{

		if (!typeof pluginFactory === 'function') {
			throw "PluginFactory must be a function.";
		}

		var plugin = pluginFactory(dest);

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
