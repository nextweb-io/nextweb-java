package io.nextweb.js.fn;

import io.nextweb.fn.Closure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportClosure;
import org.timepedia.exporter.client.Exportable;

@Export
@ExportClosure
public interface JsClosure extends Exportable, Closure<Object> {

	@Override
	public void apply(Object result);

}
