package io.nextweb;

import io.nextweb.engine.NextwebEngine;

public class Nextweb {

	private static NextwebEngine definedEngine;

	public static void injectEngine(final NextwebEngine engine) {
		definedEngine = engine;
	}

	public static Session createSession() {
		return definedEngine.createSession();
	}

	public static NextwebEngine getEngine() {
		return definedEngine;
	}

	public static void main(final String[] args) {
		System.out.println("Please see http://nextweb.io/ for more infos.");
	}

}
