package io.nextweb.jre;

import io.nextweb.common.LocalServer;
import io.nextweb.engine.Capability;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.engine.NextwebGlobal;

public class Nextweb {

	public static void assertEngine() {
		if (NextwebGlobal.isEngineInitialized()) {
			return;
		}

		// if not engine is initialized, try to fall back to reference engine
		// implementation

		try {
			final Class<?> referenceEngine = Class
					.forName("com.ononedb.nextweb.jre.OnedbNextwebEngineJre");

			NextwebGlobal.injectEngine((NextwebEngine) referenceEngine
					.newInstance());
		} catch (final Throwable e) {
			throw new IllegalStateException("Engine could not be initialized.",
					e);
		}

	}

	public static void assertStartServerCapability() {
		assertEngine();
		try {

			if (NextwebGlobal.getEngine().hasStartServerCapability()) {
				return;
			}

			// if no capability is initialized, try to fall back to reference
			// capability
			final Class<?> referenceCapability = Class
					.forName("com.ononedb.nextweb.local.jre.OnedbStartServerCapabilityJre");

			NextwebGlobal.getEngine().injectCapability(
					(Capability) referenceCapability.newInstance());

		} catch (final Throwable t) {
			throw new IllegalStateException(
					"Start server capability could not be instantiated.", t);
		}
	}

	public static LocalServer startServer(final int port) {
		assertStartServerCapability();

		return NextwebGlobal.getEngine().startServer(port);
	}

	public static void main(final String[] args) {
		System.out.println("Please see http://nextweb.io/ for more infos.");
	}

}
