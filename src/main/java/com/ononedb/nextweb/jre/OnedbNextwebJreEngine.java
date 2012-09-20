package com.ononedb.nextweb.jre;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.DefaultPluginFactory;
import one.client.jre.OneJre;
import one.core.dsl.CoreDsl;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.NoExport;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.internal.OnedbFactory;

@Export
public class OnedbNextwebJreEngine implements OnedbNextwebEngine {

	private CoreDsl dsl;

	private final ExceptionManager exceptionManager;

	@Export
	public static OnedbNextwebJreEngine init() {
		OnedbNextwebJreEngine engine = new OnedbNextwebJreEngine();
		Nextweb.injectEngine(engine);
		return engine;
	}

	@NoExport
	@Override
	public Session createSession() {

		if (dsl == null) {
			dsl = OneJre.init();
		}

		return getFactory().createSession(this, exceptionManager,
				dsl.createClient());
	}

	@NoExport
	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	@NoExport
	@Override
	public <ResultType> Result<ResultType> createResult(
			final AsyncResult<ResultType> asyncResult) {

		return new ResultImplementation<ResultType>(asyncResult);
	}

	@NoExport
	@Override
	public OnedbFactory getFactory() {

		return new OnedbFactory();
	}

	public OnedbNextwebJreEngine() {
		super();
		this.exceptionManager = new ExceptionManager(null);
		this.exceptionManager.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				throw new RuntimeException("Uncaught background exception: "
						+ t.getMessage() + " from class: " + origin.getClass(),
						t);
			}
		});
	}

	@Override
	public DefaultPluginFactory plugin() {

		return H.onedbDefaultPluginFactory();
	}

}
