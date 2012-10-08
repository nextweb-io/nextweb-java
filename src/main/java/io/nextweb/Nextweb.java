package io.nextweb;

import io.nextweb.common.LocalServer;
import io.nextweb.engine.NextwebEngine;

public class Nextweb {

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

	public static void main(final String[] args) {
		System.out.println("Please see http://nextweb.io/ for more infos.");
	}

}
