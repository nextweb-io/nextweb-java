package com.ononedb.nextweb.internal;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.GWT;
import com.ononedb.nextweb.js.OnedbNextwebJsEngine;

public class DummyEntryPoint {
	public void onModuleLoad() {
		ExporterUtil.exportAll();
		GWT.log("all exported");
		OnedbNextwebJsEngine.init();
	}
}
