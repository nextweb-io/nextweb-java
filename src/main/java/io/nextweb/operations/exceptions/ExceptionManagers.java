package io.nextweb.operations.exceptions;

import io.nextweb.promise.callbacks.Callback;
import io.nextweb.promise.exceptions.ExceptionManager;

public class ExceptionManagers {

    public static ExceptionManager fromCallback(final Callback<?> callback,
            final ExceptionManager parentExceptionManager) {

        final ExceptionManager em = new ExceptionManager(parentExceptionManager);

        if (callback.hasEagerFailureListener()) {
            em.catchExceptions(callback);
        }

        if (callback.hasEagerImpossibleListener()) {
            em.catchImpossible(callback);
        }

        if (callback.hasEagerUnauthorizedListener()) {
            em.catchImpossible(callback);
        }

        if (callback.hasEagerUndefinedListener()) {
            em.catchUndefined(callback);
        }

        return em;

    }

    // public static ExceptionManager chain(final ExceptionManager primary,
    // final ExceptionManager secondary) {
    //
    // }

}
