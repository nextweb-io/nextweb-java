package io.nextweb.engine;

import io.nextweb.fn.exceptions.ExceptionManager;

public class NextwebGlobal {

	private static NextwebEngine definedEngine;

	public static void injectEngine(final NextwebEngine engine) {
		definedEngine = engine;
		ExceptionManager.fallbackExceptionManager = engine.getExceptionManager();
	
	}

	private static NextwebEngine assertEngine() {
		if (definedEngine == null) {
			throw new IllegalStateException(
					"Please initialize at least one Nextweb engine first.");
		}
		return definedEngine;
	}

	public static boolean isEngineInitialized() {
		return definedEngine != null;
	}

	public static NextwebEngine getEngine() {
		return assertEngine();
	}

}
