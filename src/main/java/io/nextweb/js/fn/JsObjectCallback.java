package io.nextweb.js.fn;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportClosure;
import org.timepedia.exporter.client.Exportable;

@Export
@ExportClosure
public interface JsObjectCallback extends Exportable {

	public void run(Object result);

}
