package io.nextweb.js;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.engine.NextwebGlobal;
import io.nextweb.js.common.JsLocalServer;
import io.nextweb.js.engine.JsNextwebEngine;
import io.nextweb.js.engine.NextwebEngineJs;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

@ExportPackage("")
@Export("Nextweb")
public class NextwebJs implements Exportable {

	private static NextwebEngineJs injectedEngine;
	private static NextwebEngine nextwebEngine;

	@Export
	public static void injectEngine(final JsNextwebEngine engine) {
		NextwebGlobal.injectEngine(engine.getEngine());
		injectedEngine = engine.getEngine();
		nextwebEngine = engine.getEngine();
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

	@Export
	public static JsLocalServer startServer(final int port) {
		return JsLocalServer.wrap(nextwebEngine.startServer(port));
	}

	public NextwebJs() {
		super();
	}

}
