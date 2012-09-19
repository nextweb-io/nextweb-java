package com.ononedb.nextweb.internal;

import org.timepedia.exporter.client.ExporterUtil;

import com.ononedb.nextweb.js.OnedbNextwebJsEngine;

public class DummyEntryPoint {
	public void onModuleLoad() {
		ExporterUtil.exportAll();
		// GWT.log("all exported");
		OnedbNextwebJsEngine.init();

		onLoadImpl();
	}

	/**
	 * Check if a callback method has been specified and call upon successful
	 * load.
	 */
	private native void onLoadImpl() /*-{
		var callOnLoad = function() {
			if ($wnd.onNextweb && typeof $wnd.onNextweb == 'function') {
				$wnd.onNextweb();
				return true;
			}
			return false;
		};

		if (callOnLoad()) {
			return;
		}

		var myint;
		myint = setInterval(function() {
			if (callOnLoad()) {
				clearInterval(myint);
			}
		}, 20);

	}-*/;
}
