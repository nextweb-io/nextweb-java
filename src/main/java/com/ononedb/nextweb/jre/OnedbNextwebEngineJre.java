package com.ononedb.nextweb.jre;

import io.nextweb.Session;
import io.nextweb.common.LocalServer;
import io.nextweb.engine.Capability;
import io.nextweb.engine.NextwebGlobal;
import io.nextweb.engine.StartServerCapability;
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

public class OnedbNextwebEngineJre implements OnedbNextwebEngine {

	private CoreDsl dsl;

	private final ExceptionManager exceptionManager;

	private StartServerCapability startServerCapability;

	public static OnedbNextwebEngineJre init() {
		final OnedbNextwebEngineJre engine = new OnedbNextwebEngineJre();
		NextwebGlobal.injectEngine(engine);
		return engine;
	}

	public static OnedbNextwebEngineJre assertInitialized() {
		if (NextwebGlobal.getEngine() == null
				|| (!(NextwebGlobal.getEngine() instanceof OnedbNextwebEngineJre))) {
			return init();
		}
		return (OnedbNextwebEngineJre) NextwebGlobal.getEngine();
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

	/**
	 * Must have no argument constuctor.
	 */
	public OnedbNextwebEngineJre() {
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

	@Override
	public boolean hasStartServerCapability() {
		return startServerCapability != null;
	}

	@Override
	public void injectCapability(final Capability capability) {
		if (capability instanceof StartServerCapability) {
			startServerCapability = (StartServerCapability) capability;
			return;
		}

		throw new IllegalArgumentException(
				"This engine cannot recognize the capability: ["
						+ capability.getClass() + "]");
	}

	@Override
	public LocalServer startServer(final int port) {
		if (startServerCapability == null) {
			throw new IllegalStateException(
					"Please inject a StartServerCapability first.");
		}

		return startServerCapability.startServer(port);
	}

}
