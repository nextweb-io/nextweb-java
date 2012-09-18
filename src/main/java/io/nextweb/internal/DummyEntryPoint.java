package io.nextweb.internal;

import org.timepedia.exporter.client.ExporterUtil;

public class DummyEntryPoint {
	public void onModuleLoad() {
		ExporterUtil.exportAll();
	}
}
