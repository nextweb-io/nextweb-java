package com.ononedb.nextweb.jre;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.core.DefaultPluginFactory;
import one.client.jre.OneJre;
import one.core.dsl.CoreDsl;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.OnedbSession;
import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.internal.OnedbFactory;

public class OnedbNextwebJreEngine implements OnedbNextwebEngine {

	private CoreDsl dsl;

	private final ExceptionManager exceptionManager;

	public static OnedbNextwebJreEngine init() {
		OnedbNextwebJreEngine engine = new OnedbNextwebJreEngine();
		Nextweb.injectEngine(engine);
		return engine;
	}

	@Override
	public Session createSession() {

		if (dsl == null) {
			dsl = OneJre.init();
		}

		return getFactory().createSession(this, dsl.createClient());
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	@Override
	public <ResultType> Result<ResultType> createResult(
			ExceptionManager exceptionManager, Session session,
			final AsyncResult<ResultType> asyncResult) {

		return new ResultImplementation<ResultType>(session, exceptionManager,
				asyncResult);
	}

	@Override
	public OnedbFactory getFactory() {

		return new OnedbFactory();
	}

	public OnedbNextwebJreEngine() {
		super();
		this.exceptionManager = getFactory().createExceptionManager(null);
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

	@Override
	public void runSafe(Session forSession, Runnable task) {
		((OnedbSession) forSession).getClient().runSafe(task);
	}

}
