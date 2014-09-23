package io.nextweb.engine;

import io.nextweb.promise.exceptions.ExceptionManager;

public class NextwebGlobal {

    private static NextwebEngine definedEngine;

    private static StartServerCapability startServerCapability;

    public static void injectStartServerCapability(final StartServerCapability capability) {
        startServerCapability = capability;
    }

    public static StartServerCapability getStartServerCapability() {
        return startServerCapability;
    }

    public static void injectEngine(final NextwebEngine engine) {
        definedEngine = engine;
        ExceptionManager.fallbackExceptionManager = engine.getExceptionManager();

    }

    private static NextwebEngine assertEngine() {
        if (definedEngine == null) {
            throw new IllegalStateException("Please initialize at least one Nextweb engine first.");
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
