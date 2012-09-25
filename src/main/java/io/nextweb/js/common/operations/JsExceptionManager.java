package io.nextweb.js.common.operations;

import io.nextweb.js.JsWrapper;
import io.nextweb.operations.exceptions.ExceptionManager;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsExceptionManager implements Exportable,
		JsWrapper<ExceptionManager> {

	private ExceptionManager em;

	@Export
	public void onFailure(JavaScriptObject origin, String errorMessage) {
		em.onFailure(origin, new Exception(errorMessage));
	}

	public JsExceptionManager() {
		super();
	}

	@NoExport
	@Override
	public ExceptionManager getOriginal() {
		return this.em;
	}

	@NoExport
	@Override
	public void setOriginal(ExceptionManager original) {
		this.em = original;
	}

	@NoExport
	public static JsExceptionManager wrap(ExceptionManager em) {
		JsExceptionManager jsem = new JsExceptionManager();
		jsem.setOriginal(em);
		return jsem;
	}

}
