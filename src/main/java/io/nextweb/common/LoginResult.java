package io.nextweb.common;

import io.nextweb.operations.exceptions.LoginFailuresInterceptor;
import io.nextweb.operations.exceptions.LoginFailuresListener;
import io.nextweb.promise.Result;

/**
 * <p>
 * The object returned from login and register operations.
 * </p>
 * 
 * @author <a href="http://www.mxro.de/">Max Rohde</a>
 * 
 */
public interface LoginResult extends Result<User>,
		LoginFailuresInterceptor<LoginResult> {

	public LoginFailuresListener getLoginFailuresListener();

}
