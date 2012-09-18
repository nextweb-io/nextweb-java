package com.ononedb.nextweb.internal;

import org.timepedia.exporter.client.ExporterUtil;

import com.ononedb.nextweb.js.OnedbNextwebJsEngine;

public class DummyEntryPoint {
	public void onModuleLoad() {
		ExporterUtil.exportAll();

		OnedbNextwebJsEngine.init();
	}
}
