package io.nextweb.js;

import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;
import io.nextweb.js.common.JH;
import io.nextweb.js.engine.JsNextwebEngine;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsResult;
import io.nextweb.operations.callbacks.CallbackFactory;

import java.util.ArrayList;
import java.util.List;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsSession implements Exportable, JsWrapper<Session> {

	private Session session;

	@Export
	public JsNextwebEngine getEngine() {
		return JsNextwebEngine.wrap((NextwebEngineJs) session.getEngine());
	}

	@Export
	public JsResult close() {
		return JH.jsFactory(session).createResult(session.close());
	}

	@Export
	public JsLink node(final String uri) {
		return JH.jsFactory(session).createLink(session.node(uri));

	}

	@Export
	public JsLink node(final String uri, final String secret) {
		return JH.jsFactory(session).createLink(session.node(uri, secret));
	}

	@Export
	public JsResult commit() {
		return JH.jsFactory(session).createResult(session.commit());
	}

	@Export
	public JsQuery seed() {
		return JH.jsFactory(session).createQuery(session.seed());
	}

	@Export
	public JsQuery createRealm(final String realmTitle, final String realmType,
			final String apiKey) {
		return JH.jsFactory(session).createQuery(
				session.createRealm(realmTitle, realmType, apiKey));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Export
	public void getAll(final Object... params) {
		// com.google.gwt.core.client.JsArray<JavaScriptObject> jsAr = params
		// .cast();

		final Object[] jsAr = params;

		final List<Result<?>> requestedEntities = new ArrayList<Result<?>>(
				jsAr.length - 1);

		JavaScriptObject callback_onSuccess = null;
		JavaScriptObject callback_onFailure = null;

		for (int i = 0; i <= jsAr.length - 1; i++) {

			final Object param = jsAr[i];

			final Object gwtInstance = ExporterUtil.gwtInstance(param);
			// GWT.log(gwtInstance.getClass().toString());
			if (gwtInstance instanceof JsWrapper<?>) {

				if (((JsWrapper) gwtInstance).getOriginal() instanceof Result<?>) {
					// GWT.log("Adding result "
					// + gwtInstance.getClass().toString());
					requestedEntities.add((Result<?>) ((JsWrapper) gwtInstance)
							.getOriginal());
				}
			} else {
				if (callback_onSuccess != null) {
					if (callback_onFailure != null) {
						throw new IllegalArgumentException(
								"Invalid parameters for get all. Specifiy unresolved results + one callback function.");
					}
					callback_onFailure = (JavaScriptObject) param;
					continue;
				}
				// GWT.log("Adding callback: " + gwtInstance.getClass());
				callback_onSuccess = (JavaScriptObject) param;
			}

		}

		if (callback_onSuccess == null || requestedEntities.size() == 0) {
			throw new IllegalArgumentException(
					"Invalid parameters for get all. Specifiy unresolved results + one callback function.");
		}

		final JavaScriptObject callback_onSuccess_Closed = callback_onSuccess;
		final JavaScriptObject callback_onFailure_Closed = callback_onFailure;
		session.getAll(true, (Result<Object>[]) requestedEntities.toArray())
				.get(CallbackFactory.eagerCallback(session,
						session.getExceptionManager(),
						new Closure<SuccessFail>() {

							@Override
							public void apply(final SuccessFail result) {
								if (result.isFail()) {
									if (callback_onFailure_Closed == null) {
										session.getEngine()
												.getExceptionManager()
												.onFailure(
														Fn.exception(
																this,
																result.getException()));
										return;
									}

									JH.triggerCallback(
											callback_onFailure_Closed,
											((NextwebEngineJs) session
													.getEngine()).jsFactory()
													.getWrappers(),
											new JavaScriptObject[] { ExporterUtil
													.wrap(result.getException()) });
									return;
								}

								final List<Object> resolvedObjects = new ArrayList<Object>(
										requestedEntities.size());
								for (final Result<?> requestedResult : requestedEntities) {
									resolvedObjects.add(requestedResult.get());
								}

								JH.triggerCallback(callback_onSuccess_Closed,
										((NextwebEngineJs) session.getEngine())
												.jsFactory().getWrappers(),
										resolvedObjects.toArray());
							}

						}).catchExceptions(new ExceptionListener() {

					@Override
					public void onFailure(final ExceptionResult r) {
						if (callback_onFailure_Closed == null) {
							session.getEngine().getExceptionManager()
									.onFailure(r);
							return;
						}

						JH.triggerCallback(callback_onFailure_Closed,
								((NextwebEngineJs) session.getEngine())
										.jsFactory().getWrappers(),
								new JavaScriptObject[] { ExporterUtil.wrap(r
										.exception()) });
					}
				}));

	}

	@Override
	@NoExport
	public Session getOriginal() {
		return session;
	}

	@Override
	@NoExport
	public void setOriginal(final Session session) {
		this.session = session;
	}

	@NoExport
	public static JsSession wrap(final Session session) {
		final JsSession jsSession = new JsSession();
		jsSession.setOriginal(session);
		return jsSession;
	}

	public JsSession() {
		super();

	}

}
