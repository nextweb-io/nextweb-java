package com.ononedb.nextweb.local.js;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.ononedb.nextweb.OnedbNextwebEngine;

@ExportPackage("")
@Export
public class OnedbLocal implements Exportable {

	@Export
	public static void injectStartServerCapability(
			final OnedbNextwebEngine engine) {
		engine.injectCapability(new OnedbStartServerCapabilityJs());
	}

	public OnedbLocal() {
		super();
	}

}
