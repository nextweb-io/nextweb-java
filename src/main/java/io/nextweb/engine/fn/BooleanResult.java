package io.nextweb.engine.fn;

import delight.async.callbacks.ValueCallback;
import delight.functional.Closure;
import io.nextweb.Session;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.promise.BasicPromise;
import io.nextweb.promise.NextwebOperation;
import io.nextweb.promise.NextwebPromise;
import io.nextweb.promise.callbacks.NextwebCallback;
import io.nextweb.promise.exceptions.AllInterceptor;
import io.nextweb.promise.exceptions.ExceptionListener;
import io.nextweb.promise.exceptions.ImpossibleListener;
import io.nextweb.promise.exceptions.NextwebExceptionManager;
import io.nextweb.promise.exceptions.UnauthorizedListener;
import io.nextweb.promise.exceptions.UndefinedListener;

public class BooleanResult implements BasicPromise<Boolean>, AllInterceptor<BooleanResult> {

    private final NextwebPromise<Boolean> promise;
    private final NextwebExceptionManager exceptionManager;
    private final Session session;

    @Override
    public NextwebExceptionManager getExceptionManager() {
        return exceptionManager;
    }

    public BooleanResult and(final BooleanResult otherResult) {
        return new BooleanResult(exceptionManager, session, new NextwebOperation<Boolean>() {

            @Override
            public void apply(final NextwebCallback<Boolean> callback) {
                promise.apply(CallbackFactory.embeddedCallback(exceptionManager, callback, new Closure<Boolean>() {

                    @Override
                    public void apply(final Boolean o) {
                        if (!o.booleanValue()) {
                            callback.onSuccess(false);
                            return;
                        }

                        otherResult.apply(CallbackFactory.embeddedCallback(exceptionManager, callback,
                                new Closure<Boolean>() {

                                    @Override
                                    public void apply(final Boolean o) {
                                        callback.onSuccess(o);
                                    }
                                }));

                    }

                }));
            }
        });
    }

    public BooleanResult or(final BooleanResult otherResult) {
        return new BooleanResult(exceptionManager, session, new NextwebOperation<Boolean>() {

            @Override
            public void apply(final NextwebCallback<Boolean> callback) {
                promise.apply(CallbackFactory.embeddedCallback(exceptionManager, callback, new Closure<Boolean>() {

                    @Override
                    public void apply(final Boolean o) {
                        if (o.booleanValue()) {
                            callback.onSuccess(true);
                            return;
                        }

                        otherResult.apply(CallbackFactory.embeddedCallback(exceptionManager, callback,
                                new Closure<Boolean>() {

                                    @Override
                                    public void apply(final Boolean o) {
                                        callback.onSuccess(o);
                                    }
                                }));

                    }

                }));
            }
        });
    }

    @Override
    public Boolean get() {
        return promise.get();
    }

    @Override
    public void get(final Closure<Boolean> callback) {
        promise.get(callback);
    }

    @Override
    public void apply(final NextwebCallback<Boolean> callback) {
        promise.apply(callback);
    }

    @Override
    public BooleanResult catchUnauthorized(final UnauthorizedListener listener) {
        exceptionManager.catchUnauthorized(listener);
        return this;
    }

    @Override
    public BooleanResult catchImpossible(final ImpossibleListener listener) {
        exceptionManager.catchImpossible(listener);
        return this;
    }

    @Override
    public BooleanResult catchExceptions(final ExceptionListener listener) {
        exceptionManager.catchExceptions(listener);
        return this;
    }

    @Override
    public BooleanResult catchUndefined(final UndefinedListener listener) {
        exceptionManager.catchUndefined(listener);
        return this;
    }

    @Override
    public NextwebOperation<Boolean> getOriginalOperation() {
        return this.promise;
    }

    public BooleanResult(final NextwebExceptionManager eM, final Session session, final NextwebOperation<Boolean> result) {
        super();
        this.exceptionManager = new NextwebExceptionManager(eM);
        this.session = session;
        this.promise = session.getEngine().getFactory().createPromise(this.exceptionManager, session, result);

    }

    @Override
    public void apply(final ValueCallback<Boolean> callback) {
        this.promise.apply(callback);

    }
}
