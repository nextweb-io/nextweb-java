package com.ononedb.nextweb.js;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.js.NextwebJs;
import io.nextweb.js.engine.JsFactory;
import io.nextweb.js.engine.JsNextwebEngine;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.core.DefaultPluginFactory;
import nx.client.gwt.services.GwtRemoteService;
import nx.client.gwt.services.GwtRemoteServiceAsync;
import one.client.gwt.OneGwt;
import one.core.dsl.CoreDsl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.internal.OnedbFactory;
import com.ononedb.nextweb.js.fn.JsResultImplementation;
import com.ononedb.nextweb.js.utils.OnedbWrapper;

public class OnedbNextwebJsEngineImpl implements OnedbNextwebEngineJs {

	private CoreDsl dsl;
	private final ExceptionManager exceptionManager;
	private final JsFactory jsFactory;

	public static OnedbNextwebJsEngineImpl init() {
		OnedbNextwebJsEngineImpl engine = new OnedbNextwebJsEngineImpl();
		NextwebJs.injectEngine(JsNextwebEngine.wrap(engine));
		return engine;
	}

	private CoreDsl assertDsl() {
		if (dsl != null) {
			return dsl;
		}

		final GwtRemoteServiceAsync gwtService = GWT
				.create(GwtRemoteService.class);

		((ServiceDefTarget) gwtService)
				.setServiceEntryPoint("/servlets/v01/gwtrpc");

		dsl = OneGwt.init(gwtService, "");

		return dsl;
	}

	@Override
	public Session createSession() {

		CoreDsl dsl = assertDsl();

		return getFactory().createSession(this, exceptionManager,
				dsl.createClient());
	}

	@Override
	public <ResultType> Result<ResultType> createResult(
			ExceptionManager exceptionManager,
			AsyncResult<ResultType> asyncResult) {
		return new JsResultImplementation<ResultType>(exceptionManager,
				asyncResult);
	}

	@Override
	public ExceptionManager getExceptionManager() {

		return exceptionManager;
	}

	@Override
	public OnedbFactory getFactory() {
		return new OnedbFactory();
	}

	public OnedbNextwebJsEngineImpl() {
		super();
		this.exceptionManager = getFactory().createExceptionManager(null);
		this.exceptionManager.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				throw new RuntimeException("Unhandled exception: "
						+ t.getMessage() + " from object " + origin + " ("
						+ origin.getClass() + ")");
			}
		});
		this.jsFactory = new JsFactory();
		jsFactory.getWrappers().addWrapper(OnedbWrapper.ONEJSON);
	}

	@Override
	public DefaultPluginFactory plugin() {
		return H.onedbDefaultPluginFactory();
	}

	@Override
	public JsFactory jsFactory() {
		return jsFactory;
	}

}
