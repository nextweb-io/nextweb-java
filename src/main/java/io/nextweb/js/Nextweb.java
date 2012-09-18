package io.nextweb.js;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.js.engine.JsNextwebEngine;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;

@Export(value = "Nextweb")
public class Nextweb implements Exportable {

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

	public Nextweb() {
		super();
	}

}
