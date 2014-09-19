package io.nextweb.jre;

import io.nextweb.Session;
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
        // implementations

        try {
            final Class<?> referenceEngine = scanClasspath(knownEngineImplementations);

            final NextwebEngine engine = (NextwebEngine) referenceEngine.newInstance();

            NextwebGlobal.injectEngine(engine);

        } catch (final Throwable e) {
            throw new IllegalStateException("Engine could not be initialized.", e);
        }

        // throw new RuntimeException("init");

    }

    public static void assertStartServerCapability() {
        assertEngine();
        try {

            if (NextwebGlobal.getEngine().hasStartServerCapability()) {
                return;
            }

            // if no capability is initialized, try to fall back to reference
            // capabilities
            final Class<?> referenceCapability = scanClasspath(knownStartServerCapabilityImplementations);

            NextwebGlobal.getEngine().injectCapability((Capability) referenceCapability.newInstance());

        } catch (final Throwable t) {
            throw new IllegalStateException("Start server capability could not be instantiated.", t);
        }
    }

    public static LocalServer startServer(final int port) {
        assertStartServerCapability();

        return NextwebGlobal.getEngine().startServer(port);
    }

    public static Session createSession() {
        assertEngine();

        return NextwebGlobal.getEngine().createSession();
    }

    public static NextwebEngine getEngine() {
        assertEngine();

        return NextwebGlobal.getEngine();
    }

    public static void main(final String[] args) {
        System.out.println("Please see http://nextweb.io/ for more infos.");
    }

    public static String[] knownEngineImplementations = new String[] { "com.ononedb.nextweb.jre.OnedbNextwebEngineJre" };

    public static String[] knownStartServerCapabilityImplementations = new String[] { "com.ononedb.nextweb.local.jre.OnedbStartServerCapabilityJre" };

    public static Class<?> scanClasspath(final String[] knownImplementations) {

        for (final String className : knownImplementations) {
            try {
                final Class<?> referenceEngine = Class.forName(className);
                return referenceEngine;
            } catch (final ClassNotFoundException e) {
                // nothing
            }
        }

        throw new RuntimeException(new ClassNotFoundException(
                "Cannot find any known Nextweb implementation on classpath."));
    }

}
