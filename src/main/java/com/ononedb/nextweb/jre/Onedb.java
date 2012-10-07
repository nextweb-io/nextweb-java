package com.ononedb.nextweb.jre;

import io.nextweb.Nextweb;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;
import one.client.jre.OneJre;
import one.core.dsl.CoreDsl;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.OnedbSession;
import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.internal.OnedbFactory;
import com.ononedb.nextweb.plugins.DefaultPluginFactory;

public class Onedb implements OnedbNextwebEngine {

	private CoreDsl dsl;

	private final ExceptionManager exceptionManager;

	public static Onedb init() {
		final Onedb engine = new Onedb();
		Nextweb.injectEngine(engine);
		return engine;
	}

	public static Onedb assertInitialized() {
		if (Nextweb.getEngine() == null
				|| (!(Nextweb.getEngine() instanceof Onedb))) {
			return init();
		}
		return (Onedb) Nextweb.getEngine();
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
			final ExceptionManager exceptionManager, final Session session,
			final AsyncResult<ResultType> asyncResult) {

		return new ResultImplementation<ResultType>(session, exceptionManager,
				asyncResult);
	}

	@Override
	public OnedbFactory getFactory() {

		return new OnedbFactory();
	}

	public Onedb() {
		super();
		this.exceptionManager = getFactory().createExceptionManager(null);
		this.exceptionManager.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(final ExceptionResult r) {
				throw new RuntimeException("Uncaught background exception: "
						+ r.exception().getMessage() + " from class: "
						+ r.origin().getClass(), r.exception());
			}
		});
	}

	@Override
	public DefaultPluginFactory plugin() {

		return H.onedbDefaultPluginFactory();
	}

	@Override
	public void runSafe(final Session forSession, final Runnable task) {
		((OnedbSession) forSession).getClient().runSafe(task);
	}

}
