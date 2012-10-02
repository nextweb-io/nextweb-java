package io.nextweb.js;

import io.nextweb.Nextweb;
import io.nextweb.js.engine.JsNextwebEngine;
import io.nextweb.js.engine.NextwebEngineJs;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

@ExportPackage("")
@Export("Nextweb")
public class NextwebJs implements Exportable {

	private static NextwebEngineJs injectedEngine;

	@Export
	public static void injectEngine(final JsNextwebEngine engine) {
		Nextweb.injectEngine(engine.getEngine());
		injectedEngine = engine.getEngine();
	}

	@Export
	public static JsSession createSession() {
		return injectedEngine.jsFactory().createSession(
				injectedEngine.createSession());
	}

	@Export
	public static JsNextwebEngine getEngine() {
		return JsNextwebEngine.wrap(injectedEngine);
	}

	public NextwebJs() {
		super();
	}

}
