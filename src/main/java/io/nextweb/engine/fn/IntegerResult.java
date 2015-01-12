package io.nextweb.engine.fn;

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
import de.mxro.async.callbacks.ValueCallback;
import de.mxro.fn.Closure;

public class IntegerResult implements BasicPromise<Integer>, AllInterceptor<IntegerResult> {

    private final NextwebPromise<Integer> promise;
    private final NextwebExceptionManager exceptionManager;
    private final Session session;

    public IntegerResult plus(final IntegerResult otherResult) {
        return new IntegerResult(exceptionManager, session, new NextwebOperation<Integer>() {

            @Override
            public void apply(final NextwebCallback<Integer> callback) {

                promise.apply(CallbackFactory.embeddedCallback(exceptionManager, callback, new Closure<Integer>() {

                    @Override
                    public void apply(final Integer thisResultValue) {

                        otherResult.apply(CallbackFactory.embeddedCallback(exceptionManager, callback,
                                new Closure<Integer>() {

                                    @Override
                                    public void apply(final Integer otherResultValue) {
                                        callback.onSuccess(thisResultValue + otherResultValue);
                                    }
                                }));

                    }
                }));
            }
        });
    }

    public IntegerResult minus(final IntegerResult otherResult) {
        return new IntegerResult(exceptionManager, session, new NextwebOperation<Integer>() {

            @Override
            public void apply(final NextwebCallback<Integer> callback) {

                promise.apply(CallbackFactory.embeddedCallback(exceptionManager, callback, new Closure<Integer>() {

                    @Override
                    public void apply(final Integer thisResultValue) {

                        otherResult.apply(CallbackFactory.embeddedCallback(exceptionManager, callback,
                                new Closure<Integer>() {

                                    @Override
                                    public void apply(final Integer otherResultValue) {
                                        callback.onSuccess(thisResultValue - otherResultValue);
                                    }
                                }));

                    }
                }));
            }
        });
    }

    @Override
    public Integer get() {
        return promise.get();
    }

    @Override
    public void get(final Closure<Integer> callback) {
        promise.get(callback);
    }

    @Override
    public void apply(final NextwebCallback<Integer> callback) {
        promise.apply(callback);
    }

    @Override
    public NextwebExceptionManager getExceptionManager() {
        return exceptionManager;
    }

    @Override
    public IntegerResult catchUndefined(final UndefinedListener listener) {
        exceptionManager.catchUndefined(listener);
        return this;
    }

    @Override
    public IntegerResult catchUnauthorized(final UnauthorizedListener listener) {
        exceptionManager.catchUnauthorized(listener);
        return this;
    }

    @Override
    public IntegerResult catchExceptions(final ExceptionListener listener) {
        exceptionManager.catchExceptions(listener);
        return this;
    }

    @Override
    public IntegerResult catchImpossible(final ImpossibleListener listener) {
        exceptionManager.catchImpossible(listener);
        return this;
    }

    public IntegerResult(final NextwebExceptionManager parentExceptionManager, final Session session,
            final NextwebOperation<Integer> result) {
        super();
        this.exceptionManager = new NextwebExceptionManager(parentExceptionManager);
        this.session = session;
        this.promise = session.getEngine().getFactory().createPromise(this.exceptionManager, session, result);
    }

    @Override
    public NextwebOperation<Integer> getOriginalOperation() {
        return this.promise;
    }

    @Override
    public void apply(final ValueCallback<Integer> callback) {
        this.promise.apply(callback);
    }

}
