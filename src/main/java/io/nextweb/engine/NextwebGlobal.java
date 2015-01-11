package io.nextweb.engine;

import io.nextweb.promise.exceptions.NextwebExceptionManager;

/**
 * <p>
 * Global defaults for the Nextweb API.
 * <p>
 * Central point used to inject implementing engines and make them available to
 * the API.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
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
        NextwebExceptionManager.fallbackExceptionManager = engine.getExceptionManager();

    }

    public static boolean isEngineInitialized() {
        return definedEngine != null;
    }

    public static NextwebEngine getEngine() {
        if (definedEngine == null) {
            throw new IllegalStateException("Please initialize at least one Nextweb engine first.");
        }
        return definedEngine;
    }

}
