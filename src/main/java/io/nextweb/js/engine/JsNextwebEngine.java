package io.nextweb.js.engine;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.js.JsSession;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNextwebEngine implements Exportable {

	private NextwebEngine engine;

	@Export
	public JsSession createSession() {
		return JsSession.wrap(engine.createSession());
	}

	@NoExport
	public NextwebEngine getEngine() {
		return engine;
	}

	@NoExport
	public void setEngine(NextwebEngine engine) {
		this.engine = engine;
	}

	@NoExport
	public static JsNextwebEngine wrap(NextwebEngine engine) {
		JsNextwebEngine jsEngine = new JsNextwebEngine();
		jsEngine.setEngine(engine);
		return jsEngine;
	}

	public JsNextwebEngine() {
		super();
	}

}
