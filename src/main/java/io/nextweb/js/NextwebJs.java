package io.nextweb.js;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.js.engine.JsNextwebEngine;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

@ExportPackage("")
@Export("Nextweb")
public class NextwebJs implements Exportable {

	private static NextwebEngine injectedEngine;

	@Export
	public static void injectEngine(JsNextwebEngine engine) {
		injectedEngine = engine.getEngine();
	}

	@Export
	public static JsSession createSession() {
		return JsSession.wrap(injectedEngine.createSession());
	}

	@Export
	public static JsNextwebEngine getEngine() {
		return JsNextwebEngine.wrap(injectedEngine);
	}

	public NextwebJs() {
		super();
	}

}
