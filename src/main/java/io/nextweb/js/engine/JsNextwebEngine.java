package io.nextweb.js.engine;

import io.nextweb.js.JsSession;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNextwebEngine implements Exportable {

	private NextwebEngineJs engine;

	@Export
	public JsSession createSession() {
		return engine.jsFactory().createSession(engine.createSession());
	}

	@Export
	public JsFactory plugins() {
		return engine.jsFactory();
	}

	@NoExport
	public NextwebEngineJs getEngine() {
		return engine;
	}

	@NoExport
	public void setEngine(NextwebEngineJs engine) {
		this.engine = engine;
	}

	@NoExport
	public static JsNextwebEngine wrap(NextwebEngineJs engine) {
		JsNextwebEngine jsEngine = new JsNextwebEngine();
		jsEngine.setEngine(engine);
		return jsEngine;
	}

	public JsNextwebEngine() {
		super();

	}

}
