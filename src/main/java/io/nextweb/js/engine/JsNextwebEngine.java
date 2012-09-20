package io.nextweb.js.engine;

import io.nextweb.js.JsSession;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNextwebEngine implements Exportable {

	private NextwebEngineJs engine;
	private final JsFactory factory;

	@Export
	public JsSession createSession() {
		return JsSession.wrap(engine.createSession());
	}

	@Export
	public JsFactory plugins() {
		return factory;
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
		this.factory = new JsFactory();
	}

}
