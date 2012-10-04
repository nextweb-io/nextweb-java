package io.nextweb.common;

import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.LoginFailuresInterceptor;

public interface LoginResult extends Result<User>,
		LoginFailuresInterceptor<LoginResult> {

}
