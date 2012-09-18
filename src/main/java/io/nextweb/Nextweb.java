package io.nextweb;

import io.nextweb.engine.NextwebEngine;

public class Nextweb {

	private static NextwebEngine definedEngine;

	public static void injectEngine(NextwebEngine engine) {
		definedEngine = engine;
	}

	public static Session createSession() {
		return definedEngine.createSession();
	}

	public static NextwebEngine getEngine() {
		return definedEngine;
	}

}
