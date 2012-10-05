package com.ononedb.nextweb.internal;

import io.nextweb.common.LoginResult;
import io.nextweb.common.User;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.Result;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.operations.exceptions.ImpossibleListener;
import io.nextweb.operations.exceptions.LoginFailuresListener;
import io.nextweb.operations.exceptions.UnauthorizedListener;
import io.nextweb.operations.exceptions.UndefinedListener;

public class LoginResultImpl implements LoginResult {

	private Result<User> userResult;
	private LoginFailuresListener failureListener;

	@Override
	public User get() {
		return userResult.get();
	}

	@Override
	public void get(final Closure<User> callback) {
		userResult.get(callback);
	}

	@Override
	public ExceptionManager getExceptionManager() {
		return userResult.getExceptionManager();
	}

	@Override
	public void get(final Callback<User> callback) {
		userResult.get(callback);
	}

	@Override
	public Result<User> catchExceptions(final ExceptionListener listener) {
		userResult.catchExceptions(listener);
		return this;
	}

	@Override
	public Result<User> catchUnauthorized(final UnauthorizedListener listener) {
		userResult.catchUnauthorized(listener);
		return this;
	}

	@Override
	public Result<User> catchUndefined(final UndefinedListener listener) {
		userResult.catchUndefined(listener);
		return this;
	}

	@Override
	public Result<User> catchImpossible(final ImpossibleListener listener) {
		userResult.catchImpossible(listener);
		return this;
	}

	@Override
	public LoginResult catchLoginFailures(final LoginFailuresListener listener) {
		failureListener = listener;
		return this;
	}

	@Override
	public LoginFailuresListener getLoginFailuresListener() {
		return this.failureListener;
	}

	public void setUserResult(final Result<User> userResult) {
		this.userResult = userResult;
	}

}
