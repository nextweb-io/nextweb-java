package io.nextweb.js;

import io.nextweb.Session;
import io.nextweb.common.Postbox;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.engine.JsNextwebEngine;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.fn.JsResult;
import io.nextweb.js.operations.JsExceptionListeners;
import io.nextweb.js.operations.impl.JsOpCommon;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;

import java.util.ArrayList;
import java.util.List;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsSession implements Exportable, JsWrapper<Session>,
		JsExceptionListeners<JsSession> {

	private Session session;

	@Export
	public JsNextwebEngine getEngine() {
		return JsNextwebEngine.wrap((NextwebEngineJs) session.getEngine());
	}

	@Export
	public JsExceptionManager getExceptionManager() {
		return JsExceptionManager.wrap(session.getExceptionManager());
	}

	@Export
	@Override
	public JsSession catchExceptions(final JsClosure exceptionListener) {
		getExceptionManager().catchExceptions(exceptionListener);
		return this;
	}

	@Export
	@Override
	public JsSession catchUndefined(final JsClosure undefinedListener) {
		getExceptionManager().catchUndefined(undefinedListener);
		return this;
	}

	@Export
	@Override
	public JsSession catchUnauthorized(final JsClosure unauthorizedListener) {
		getExceptionManager().catchUnauthorized(unauthorizedListener);
		return this;
	}

	@Export
	@Override
	public JsSession catchImpossible(final JsClosure impossibleListener) {
		getExceptionManager().catchImpossible(impossibleListener);
		return this;
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
	public JsResult post(final Object value, final String toUri,
			final String secret) {
		final Object javaValue = JsOpCommon.getJavaValue(session, value);

		assert !(javaValue instanceof JsQuery)
				&& !(javaValue instanceof JsLink)
				&& !(javaValue instanceof JsNode) : "Entities cannot be posted. Please post values.";

		return JH.jsFactory(session).createResult(
				session.post(javaValue, toUri, secret));
	}

	@Export
	public JsQuery seed() {
		return JH.jsFactory(session).createQuery(session.seed());
	}

	@Export
	public JsQuery seed(final String seedType) {
		return JH.jsFactory(session).createQuery(session.seed(seedType));
	}

	@Export
	public JsQuery createRealm(final String realmTitle, final String realmType,
			final String apiKey) {
		return JH.jsFactory(session).createQuery(
				session.createRealm(realmTitle, realmType, apiKey));
	}

	@Export
	public JsResult createPostbox(final String postboxTitle,
			final String postboxType, final String apiKey) {
		return JH.jsFactory(session).createResult(
				session.getEngine().createResult(session.getExceptionManager(),
						session, new AsyncResult<Object>() {

							@Override
							public void get(final Callback<Object> callback) {
								session.createPostbox(postboxTitle,
										postboxType, apiKey).get(
										CallbackFactory.eagerCallback(session,
												session.getExceptionManager(),
												new Closure<Postbox>() {

													@Override
													public void apply(
															final Postbox o) {
														final JavaScriptObject node = ExporterUtil
																.wrap(JH.jsFactory(
																		session)
																		.createNode(
																				o.node()));

														callback.onSuccess(wrapPostbox(
																node,
																o.partnerSecret()));

													}

													private final native JavaScriptObject wrapPostbox(
															JavaScriptObject node,
															String partnerSecret)/*-{
																						return {
																						node: node,
																						partnerSecret: partnerSecret
																						};
																					}-*/;

												}));
							}
						}));
	}

	@SuppressWarnings({ "rawtypes" })
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

		final Result[] requestedEntitiesArray = requestedEntities
				.toArray(new Result[] {});

		session.getAll(true, requestedEntitiesArray).get(
				CallbackFactory.eagerCallback(session,
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
