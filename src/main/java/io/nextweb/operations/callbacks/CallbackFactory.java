package io.nextweb.operations.callbacks;

import de.mxro.fn.Closure;
import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.LinkListQuery;
import io.nextweb.Session;
import io.nextweb.promise.callbacks.NextwebCallback;
import io.nextweb.promise.callbacks.EmbeddedCallback;
import io.nextweb.promise.exceptions.NextwebExceptionManager;

public class CallbackFactory {

    @SuppressWarnings("unchecked")
    public static <ResultType> NextwebCallback<ResultType> embeddedCallback(
            final NextwebExceptionManager exceptionManager,
            final NextwebCallback<ResultType> embeddedIn) {
        return new EmbeddedCallback<ResultType>((NextwebCallback<Object>) embeddedIn,
                exceptionManager);
    }

    @SuppressWarnings("unchecked")
    public static <ResultType> NextwebCallback<ResultType> embeddedCallback(
            final NextwebExceptionManager exceptionManager,
            final NextwebCallback<?> embeddedIn, final Closure<ResultType> p_onSuccess) {
        return new EmbeddedCallback<ResultType>((NextwebCallback<Object>) embeddedIn,
                exceptionManager) {

            @Override
            public void onSuccess(final ResultType result) {
                p_onSuccess.apply(result);
            }

        };
    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(
            final Entity entity, final Closure<ResultType> closure) {
        return lazyCallback(entity.getSession(), entity.getExceptionManager(),
                closure);
    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(
            final LinkListQuery entity, final Closure<ResultType> closure) {
        return lazyCallback(entity.getSession(), entity.getExceptionManager(),
                closure);
    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(
            final EntityList entity, final Closure<ResultType> closure) {

        return lazyCallback(entity.getSession(), entity.getExceptionManager(),
                closure);

    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(
            final Session session, final NextwebExceptionManager exceptionManager,
            final Closure<ResultType> closure) {

        return new LazyCallback<ResultType>(exceptionManager, session) {

            @Override
            public void onSuccess(final ResultType result) {
                closure.apply(result);
            }
        };

    }

    public static <ResultType> EagerCallback<ResultType> eagerCallback(
            final Session session, final NextwebExceptionManager exceptionManager,
            final Closure<ResultType> closure) {
        return new EagerCallback<ResultType>(session, exceptionManager, null) {

            @Override
            public void onSuccess(final ResultType result) {
                closure.apply(result);
            }
        };
    }

    /**
     * Call eager op first but immideately call fallback callback.
     * 
     * @param session
     * @param fallbackCallback
     * @param exceptionManager
     * @param closure
     * @return
     */
    public static <ResultType> EagerCallback<ResultType> eagerCallback(
            final Session session, final NextwebCallback<?> fallbackCallback,
            final NextwebExceptionManager exceptionManager,
            final Closure<ResultType> closure) {
        return new EagerCallback<ResultType>(session, exceptionManager,
                fallbackCallback) {

            @Override
            public void onSuccess(final ResultType result) {
                closure.apply(result);
            }
        };
    }

}
