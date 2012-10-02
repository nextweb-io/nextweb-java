package io.nextweb.js.common.operations;

import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.js.JsWrapper;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.operations.JsExceptionListeners;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsExceptionManager implements Exportable,
		JsWrapper<ExceptionManager>, JsExceptionListeners {

	private ExceptionManager em;

	@Export
	public void onFailure(final JavaScriptObject origin,
			final String errorMessage) {
		em.onFailure(Fn.exception(origin, new Exception(errorMessage)));
	}

	@Override
	@Export
	public JsExceptionManager catchExceptions(final JsClosure exceptionListener) {
		em.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(final ExceptionResult r) {
				exceptionListener.apply(JsExceptionManager.wrapExceptionResult(
						r.origin().getClass().toString(), r.exception()));
			}
		});

		return this;
	}

	@Override
	public JsExceptionManager catchUndefined(final JsClosure undefinedListener) {
		em.catchUndefined(new UndefinedListener() {

			@Override
			public void onUndefined(final UndefinedResult r) {
				undefinedListener.apply(wrapUndefinedResult(r.origin()
						.getClass().toString(), r.message()));
			}
		});
		return this;
	}

	@Override
	public JsExceptionManager catchUnauthorized(
			final JsClosure unauthorizedListener) {
		em.catchUnauthorized(new UnauthorizedListener() {

			@Override
			public void onUnauthorized(final UnauthorizedResult r) {
				unauthorizedListener.apply(wrapUnauthorizedResult(r.origin()
						.getClass().toString(), r.getMessage(), r.getType()
						.getClass().toString()));
			}
		});
		return this;
	}

	@Override
	public JsExceptionManager catchImpossible(final JsClosure impossibleListener) {
		em.c
		return this;
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
	public void setOriginal(final ExceptionManager original) {
		this.em = original;
	}

	public static final native JavaScriptObject wrapExceptionResult(
			String origin, Throwable t)/*-{
										return {
										exception: t,
										origin: origin
										}
										}-*/;

	public static final native JavaScriptObject wrapUndefinedResult(
			String origin, String message)/*-{
											return {
											message: message,
											origin: origin
											}
											}-*/;

	public static final native JavaScriptObject wrapUnauthorizedResult(
			String origin, String message, String type)/*-{
														return {
														message: message,
														origin: origin,
														type: type
														}
														}-*/;

	@NoExport
	public static JsExceptionManager wrap(final ExceptionManager em) {
		final JsExceptionManager jsem = new JsExceptionManager();
		jsem.setOriginal(em);
		return jsem;
	}

}
