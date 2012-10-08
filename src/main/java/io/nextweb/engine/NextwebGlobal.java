package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.common.LocalServer;

public class NextwebGlobal {

	private static NextwebEngine definedEngine;

	public static void injectEngine(final NextwebEngine engine) {
		definedEngine = engine;
	}

	private static NextwebEngine assertEngine() {
		return definedEngine;
	}

	public static Session createSession() {
		return assertEngine().createSession();
	}

	public static LocalServer startServer(final int port) {
		return assertEngine().startServer(port);
	}

	public static NextwebEngine getEngine() {
		return assertEngine();
	}

}
