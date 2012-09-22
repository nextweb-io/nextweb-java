package io.nextweb.js;

import io.nextweb.Session;
import io.nextweb.fn.Result;
import io.nextweb.fn.RequestResultCallback;
import io.nextweb.fn.SuccessFail;
import io.nextweb.js.common.JH;
import io.nextweb.js.engine.JsNextwebEngine;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsResult;

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
		return JsResult.wrap(session.close());
	}

	@Export
	public JsLink node(String uri) {
		return ((NextwebEngineJs) session.getEngine()).jsFactory().createLink(
				session.node(uri));

	}

	@SuppressWarnings("rawtypes")
	@Export
	public void getAll(Object... params) {
		// com.google.gwt.core.client.JsArray<JavaScriptObject> jsAr = params
		// .cast();

		Object[] jsAr = params;

		final List<Result<?>> requestedEntities = new ArrayList<Result<?>>(
				jsAr.length - 1);

		JavaScriptObject callback_onSuccess = null;
		JavaScriptObject callback_onFailure = null;

		for (int i = 0; i <= jsAr.length - 1; i++) {

			Object param = jsAr[i];

			Object gwtInstance = ExporterUtil.gwtInstance(param);
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
		session.getAll(requestedEntities.toArray(new Result<?>[0])).get(
				new RequestResultCallback<SuccessFail>() {

					@Override
					public void onSuccess(SuccessFail result) {
						List<Object> resolvedObjects = new ArrayList<Object>(
								requestedEntities.size());
						for (Result<?> requestedResult : requestedEntities) {
							resolvedObjects.add(requestedResult.get());
						}

						JH.triggerCallback(callback_onSuccess_Closed,
								((NextwebEngineJs) session.getEngine())
										.jsFactory().getWrappers(),
								resolvedObjects.toArray());
					}

					@Override
					public void onFailure(Throwable t) {
						if (callback_onFailure_Closed == null) {
							session.getEngine().getExceptionManager()
									.onFailure(this, t);
							return;
						}

						JH.triggerCallback(callback_onFailure_Closed,
								((NextwebEngineJs) session.getEngine())
										.jsFactory().getWrappers(),
								new JavaScriptObject[] { ExporterUtil.wrap(t) });
					}

				});

	}

	@Override
	@NoExport
	public Session getOriginal() {
		return session;
	}

	@Override
	@NoExport
	public void setOriginal(Session session) {
		this.session = session;
	}

	@NoExport
	public static JsSession wrap(Session session) {
		JsSession jsSession = new JsSession();
		jsSession.setOriginal(session);
		return jsSession;
	}

	@Export
	public final native void getAllOld(Object... args)/*-{

		if (arguments.length === 0) {
			throw "Specify nodes and a callback when calling getAll()";
		}

		//this.@io.nextweb.js.JsSession::getAll(Lcom/google/gwt/core/client/JavaScriptObject;)(arguments);

	}-*/;

	public JsSession() {
		super();

	}

}
