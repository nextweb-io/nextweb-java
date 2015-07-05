package io.nextweb.operations.callbacks;

import delight.async.callbacks.ValueCallback;
import delight.functional.Closure;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.LinkListQuery;
import io.nextweb.Session;
import io.nextweb.promise.callbacks.EmbeddedCallback;
import io.nextweb.promise.callbacks.NextwebCallback;
import io.nextweb.promise.exceptions.ExceptionResult;
import io.nextweb.promise.exceptions.ImpossibleException;
import io.nextweb.promise.exceptions.ImpossibleResult;
import io.nextweb.promise.exceptions.NextwebExceptionManager;
import io.nextweb.promise.exceptions.UnauthorizedException;
import io.nextweb.promise.exceptions.UnauthorizedResult;
import io.nextweb.promise.exceptions.UndefinedException;
import io.nextweb.promise.exceptions.UndefinedResult;

public class CallbackFactory {

    @SuppressWarnings("unchecked")
    public static <ResultType> NextwebCallback<ResultType> embeddedCallback(
            final NextwebExceptionManager exceptionManager, final NextwebCallback<ResultType> embeddedIn) {
        return new EmbeddedCallback<ResultType>((NextwebCallback<Object>) embeddedIn, exceptionManager);
    }

    @SuppressWarnings("unchecked")
    public static <ResultType> NextwebCallback<ResultType> embeddedCallback(
            final NextwebExceptionManager exceptionManager, final NextwebCallback<?> embeddedIn,
            final Closure<ResultType> p_onSuccess) {
        return new EmbeddedCallback<ResultType>((NextwebCallback<Object>) embeddedIn, exceptionManager) {

            @Override
            public void onSuccess(final ResultType result) {
                p_onSuccess.apply(result);
            }

        };
    }

    public static <ResultType> NextwebCallback<ResultType> wrap(final ValueCallback<ResultType> callback) {
        return new NextwebCallback<ResultType>() {

            @Override
            public void onFailure(final ExceptionResult r) {
                callback.onFailure(r.exception());
            }

            @Override
            public void onUnauthorized(final UnauthorizedResult r) {
                callback.onFailure(new UnauthorizedException(r));
            }

            @Override
            public void onUndefined(final UndefinedResult r) {

                callback.onFailure(new UndefinedException(r));
            }

            @Override
            public void onImpossible(final ImpossibleResult ir) {
                callback.onFailure(new ImpossibleException(ir));
            }

            @Override
            public void onSuccess(final ResultType result) {
                callback.onSuccess(result);
            }

            @Override
            public boolean hasEagerFailureListener() {
                return true;
            }

            @Override
            public boolean hasEagerUndefinedListener() {
                return true;
            }

            @Override
            public boolean hasEagerUnauthorizedListener() {
                return true;
            }

            @Override
            public boolean hasEagerImpossibleListener() {
                return true;
            }

            @Override
            public boolean hasEagerListeners() {
                return true;
            }

        };
    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(final Entity entity,
            final Closure<ResultType> closure) {
        return lazyCallback(entity.getSession(), entity.getExceptionManager(), closure);
    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(final LinkListQuery entity,
            final Closure<ResultType> closure) {
        return lazyCallback(entity.getSession(), entity.getExceptionManager(), closure);
    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(final EntityList entity,
            final Closure<ResultType> closure) {

        return lazyCallback(entity.getSession(), entity.getExceptionManager(), closure);

    }

    public static <ResultType> NextwebCallback<ResultType> lazyCallback(final Session session,
            final NextwebExceptionManager exceptionManager, final Closure<ResultType> closure) {

        return new LazyCallback<ResultType>(exceptionManager, session) {

            @Override
            public void onSuccess(final ResultType result) {
                closure.apply(result);
            }
        };

    }

    public static <ResultType> EagerCallback<ResultType> eagerCallback(final Session session,
            final NextwebExceptionManager exceptionManager, final Closure<ResultType> closure) {
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
    public static <ResultType> EagerCallback<ResultType> eagerCallback(final Session session,
            final NextwebCallback<?> fallbackCallback, final NextwebExceptionManager exceptionManager,
            final Closure<ResultType> closure) {
        return new EagerCallback<ResultType>(session, exceptionManager, fallbackCallback) {

            @Override
            public void onSuccess(final ResultType result) {
                closure.apply(result);
            }
        };
    }

}
