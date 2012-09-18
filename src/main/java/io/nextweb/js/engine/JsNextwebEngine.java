package io.nextweb.js.engine;

import io.nextweb.engine.NextwebEngine;

import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsNextwebEngine implements Exportable {

	private NextwebEngine engine;

	@NoExport
	public NextwebEngine getEngine() {
		return engine;
	}

	@NoExport
	public void setEngine(NextwebEngine engine) {
		this.engine = engine;
	}

}
