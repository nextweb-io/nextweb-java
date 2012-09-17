package com.ononedb.nextweb.jre;

import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import one.client.jre.OneJre;
import one.core.dsl.CoreDsl;

import com.ononedb.nextweb.OnedbSession;

public class OnedbNextwebJreEngine implements NextwebEngine {

	private CoreDsl dsl;

	@Override
	public Session createSession() {

		if (dsl == null) {
			dsl = OneJre.init();
		}

		return new OnedbSession(dsl.createClient());
	}

	@Override
	public void unhandledException(Object context, Throwable t) {
		throw new RuntimeException(t);
	}

	@Override
	public <ResultType> Result<ResultType> createResult(
			final AsyncResult<ResultType> asyncResult) {

		return new Result<ResultType>() {

			ResultType cached = null;
			AtomicBoolean requesting;
			List<ResultCallback<ResultType>> deferredCalls = new LinkedList<ResultCallback<ResultType>>();
			ExceptionListener exceptionListener;

			@Override
			public synchronized void get(
					final ResultCallback<ResultType> callback) {
				if (cached != null) {
					callback.onSuccess(cached);
					return;
				}
				if (requesting.get()) {
					deferredCalls.add(callback);
					return;
				}
				requesting.set(true);

				asyncResult.get(new ResultCallback<ResultType>() {

					@Override
					public void onSuccess(ResultType result) {
						cached = result;
						requesting.set(false);
						callback.onSuccess(result);

						for (ResultCallback<ResultType> deferredCallback : deferredCalls) {
							deferredCallback.onSuccess(result);
						}

					}

					@Override
					public void onFailure(Throwable t) {
						requesting.set(false);
						if (exceptionListener == null) {
							callback.onFailure(t);
							for (ResultCallback<ResultType> deferredCallback : deferredCalls) {
								deferredCallback.onFailure(t);
							}
							return;
						}
						exceptionListener.onFailure(t);
					}

				});
			}

			@Override
			public ResultType get() {

				return null;
			}

			@Override
			public void catchExceptions(ExceptionListener listener) {
				exceptionListener = listener;
			}

		};
	}
}
