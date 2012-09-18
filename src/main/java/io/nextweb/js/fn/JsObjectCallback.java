package io.nextweb.js.fn;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportClosure;

@Export
@ExportClosure
public interface JsObjectCallback {

	public void run(Object result);

}
