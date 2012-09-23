package com.ononedb.nextweb.jre;

import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.RequestCallback;
import io.nextweb.fn.RequestCallbackImpl;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.operations.exceptions.ExceptionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ResultImplementation<ResultType> implements
		Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;
	private final AtomicBoolean requesting;
	private final List<RequestCallback<ResultType>> deferredCalls;
	private final ExceptionManager exceptionManager;

	ResultType cached = null;

	public ResultImplementation(ExceptionManager exceptionManager,
			AsyncResult<ResultType> asyncResult) {
		super();
		this.asyncResult = asyncResult;
		this.exceptionManager = exceptionManager;
		this.deferredCalls = new LinkedList<RequestCallback<ResultType>>();
		requesting = new AtomicBoolean();
	}

	@Override
	public synchronized void get(final RequestCallback<ResultType> callback) {
		if (cached != null) {
			callback.onSuccess(cached);
			return;
		}
		if (requesting.get()) {
			deferredCalls.add(callback);
			return;
		}
		requesting.set(true);

		asyncResult.get(new RequestCallbackImpl<ResultType>(exceptionManager,
				callback) {

			@Override
			public void onSuccess(ResultType result) {
				cached = result;
				requesting.set(false);
				callback.onSuccess(result);

				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onSuccess(result);
				}
				deferredCalls.clear();

			}

			@Override
			public void onFailure(Object origin, Throwable t) {
				requesting.set(false);

				callback.onFailure(origin, t);

				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onFailure(origin, t);
				}
				deferredCalls.clear();
			}

			@Override
			public void onUnauthorized(Object origin,
					AuthorizationExceptionResult r) {
				requesting.set(false);

				callback.onUnauthorized(origin, r);

				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onUnauthorized(origin, r);
				}
				deferredCalls.clear();
			}

			@Override
			public void onUndefined(Object origin, String message) {
				requesting.set(false);

				callback.onUndefined(origin, message);

				for (RequestCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onUndefined(origin, message);
				}
				deferredCalls.clear();
			}

		});
	}

	@Override
	public ResultType get() {

		final CountDownLatch latch = new CountDownLatch(2);

		final List<Throwable> exceptionList = Collections
				.synchronizedList(new ArrayList<Throwable>(1));

		get(new RequestCallback<ResultType>() {

			@Override
			public void onSuccess(ResultType result) {
				latch.countDown();
			}

			@Override
			public void onFailure(Object origin, Throwable t) {
				exceptionList.add(t);
				latch.countDown();
			}

			@Override
			public void onUnauthorized(Object origin,
					AuthorizationExceptionResult r) {
				exceptionList.add(new Exception("Unauthorized access to node: "
						+ r.getMessage()));
				latch.countDown();
			}

			@Override
			public void onUndefined(Object origin, String message) {
				exceptionList
						.add(new Exception("Node not defined: " + message));
				latch.countDown();
			}

		});

		latch.countDown();
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		if (exceptionList.size() > 0) {
			throw new RuntimeException(
					"Get call could not be completed successfully. An exception was thrown.",
					exceptionList.get(0));
		}

		assert cached != null;

		return cached;
	}

	@Override
	public void get(final Closure<ResultType> callback) {
		get(new RequestCallbackImpl<ResultType>(exceptionManager, null) {

			@Override
			public void onSuccess(ResultType result) {
				callback.apply(result);
			}
		});
	}

}