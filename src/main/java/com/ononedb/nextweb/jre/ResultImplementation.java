package com.ononedb.nextweb.jre;

import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

final class ResultImplementation<ResultType> implements Result<ResultType> {

	private final AsyncResult<ResultType> asyncResult;
	ResultType cached = null;
	AtomicBoolean requesting = new AtomicBoolean();
	List<ResultCallback<ResultType>> deferredCalls = new LinkedList<ResultCallback<ResultType>>();
	ExceptionListener exceptionListener;

	ResultImplementation(AsyncResult<ResultType> asyncResult) {
		this.asyncResult = asyncResult;
	}

	@Override
	public synchronized void get(final ResultCallback<ResultType> callback) {
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
				deferredCalls.clear();

			}

			@Override
			public void onFailure(Throwable t) {
				requesting.set(false);

				callback.onFailure(t);

				for (ResultCallback<ResultType> deferredCallback : deferredCalls) {
					deferredCallback.onFailure(t);
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

		get(new ResultCallback<ResultType>() {

			@Override
			public void onSuccess(ResultType result) {
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				exceptionList.add(t);
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

}