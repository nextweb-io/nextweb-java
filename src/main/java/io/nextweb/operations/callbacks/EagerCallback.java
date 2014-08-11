package io.nextweb.operations.callbacks;

import io.nextweb.Session;
import io.nextweb.engine.NextwebGlobal;
import io.nextweb.promise.Fn;
import io.nextweb.promise.callbacks.Callback;
import io.nextweb.promise.exceptions.ExceptionListener;
import io.nextweb.promise.exceptions.ExceptionManager;
import io.nextweb.promise.exceptions.ExceptionResult;
import io.nextweb.promise.exceptions.ImpossibleListener;
import io.nextweb.promise.exceptions.ImpossibleResult;
import io.nextweb.promise.exceptions.UnauthorizedListener;
import io.nextweb.promise.exceptions.UnauthorizedResult;
import io.nextweb.promise.exceptions.UndefinedListener;
import io.nextweb.promise.exceptions.UndefinedResult;

public abstract class EagerCallback<ResultType> implements Callback<ResultType> {

    private boolean hasEagerFailureListener;
    private boolean hasEagerUndefinedListener;
    private boolean hasEagerUnauthorizedListener;
    private boolean hasEagerImpossibleListener;
    private ExceptionListener exceptionListener;
    private final Session session;
    private final ExceptionManager exceptionManager;
    private UnauthorizedListener authExceptionListener;
    private UndefinedListener undefinedExceptionListenr;
    private ImpossibleListener impossibleListener;
    private final Callback<?> fallbackCallback;

    public EagerCallback<ResultType> catchExceptions(
            final ExceptionListener exceptionListener) {
        hasEagerFailureListener = true;
        this.exceptionListener = exceptionListener;
        return this;
    }

    public EagerCallback<ResultType> catchUnauthorized(
            final UnauthorizedListener exceptionListener) {
        hasEagerUnauthorizedListener = true;
        this.authExceptionListener = exceptionListener;
        return this;
    }

    public EagerCallback<ResultType> catchUndefined(
            final UndefinedListener listener) {
        hasEagerUndefinedListener = true;
        this.undefinedExceptionListenr = listener;
        return this;
    }

    public EagerCallback<ResultType> catchImpossible(
            final ImpossibleListener listener) {
        hasEagerImpossibleListener = true;
        this.impossibleListener = listener;
        return this;
    }

    @Override
    public final void onFailure(final ExceptionResult r) {
        if (hasEagerFailureListener) {
            this.exceptionListener.onFailure(r);
            return;
        }

        if (fallbackCallback != null) {
            fallbackCallback.onFailure(r);
            return;
        }

        if (exceptionManager.canCatchExceptions()) {
            exceptionManager.onFailure(r);
            return;
        }

        if (session != null
                && session.getExceptionManager().canCatchExceptions()) {
            session.getExceptionManager().onFailure(r);
            return;
        }

        NextwebGlobal.getEngine().getExceptionManager().onFailure(r);

    }

    @Override
    public final void onUnauthorized(final UnauthorizedResult r) {
        if (hasEagerUnauthorizedListener) {
            this.authExceptionListener.onUnauthorized(r);
            return;
        }

        if (hasEagerFailureListener) {
            this.exceptionListener.onFailure(Fn.exception(r.origin(),
                    new Exception("Unauthorized: " + r.getMessage())));
            return;
        }

        if (fallbackCallback != null) {
            fallbackCallback.onUnauthorized(r);
            return;
        }

        if (exceptionManager.canCatchAuthorizationExceptions()) {
            exceptionManager.onUnauthorized(r);
            return;
        }

        if (session != null
                && session.getExceptionManager()
                        .canCatchAuthorizationExceptions()) {
            session.getExceptionManager().onUnauthorized(r);
            return;
        }

        NextwebGlobal.getEngine().getExceptionManager().onUnauthorized(r);
    }

    @Override
    public void onImpossible(final ImpossibleResult ir) {
        if (hasEagerImpossibleListener) {
            this.impossibleListener.onImpossible(ir);
            return;
        }

        if (hasEagerFailureListener) {
            this.exceptionListener.onFailure(Fn.exception(ir.origin(),
                    new Exception("Operation impossible: [" + ir.message()
                            + "]")));
            return;
        }

        if (fallbackCallback != null) {
            fallbackCallback.onImpossible(ir);
            return;
        }

        if (exceptionManager.canCatchImpossibe()) {
            exceptionManager.onImpossible(ir);
            return;
        }

        if (session != null
                && session.getExceptionManager().canCatchImpossibe()) {
            session.getExceptionManager().onImpossible(ir);
            return;
        }

        NextwebGlobal.getEngine().getExceptionManager().onImpossible(ir);

    }

    @Override
    public boolean hasEagerImpossibleListener() {
        return hasEagerImpossibleListener || hasEagerFailureListener;
    }

    @Override
    public final void onUndefined(final UndefinedResult r) {
        if (hasEagerUndefinedListener) {
            this.undefinedExceptionListenr.onUndefined(r);
            return;
        }

        if (hasEagerFailureListener) {
            this.exceptionListener.onFailure(Fn.exception(r.origin(),
                    new Exception("Undefined: " + r.message())));
            return;
        }

        if (fallbackCallback != null) {
            fallbackCallback.onUndefined(r);
            return;
        }

        if (exceptionManager.canCatchUndefinedExceptions()) {
            exceptionManager.onUndefined(r);
            return;
        }

        if (session != null
                && session.getExceptionManager().canCatchUndefinedExceptions()) {
            session.getExceptionManager().onUndefined(r);
            return;
        }

        NextwebGlobal.getEngine().getExceptionManager().onUndefined(r);
    }

    @Override
    public boolean hasEagerFailureListener() {
        return hasEagerFailureListener;
    }

    @Override
    public boolean hasEagerUndefinedListener() {

        return hasEagerUndefinedListener || hasEagerFailureListener;
    }

    @Override
    public boolean hasEagerUnauthorizedListener() {

        return hasEagerUnauthorizedListener || hasEagerFailureListener;
    }

    public EagerCallback(final Session session,
            final ExceptionManager exceptionManager,
            final Callback<?> fallbackCallback) {
        super();
        this.session = session;
        this.exceptionManager = exceptionManager;
        this.fallbackCallback = fallbackCallback;
    }

}
