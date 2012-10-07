package io.nextweb.js.common;

import io.nextweb.Session;
import io.nextweb.common.LoginResult;
import io.nextweb.fn.BasicResult;
import io.nextweb.js.fn.JsBaseResult;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.fn.JsResult;
import io.nextweb.operations.exceptions.ChallengedResult;
import io.nextweb.operations.exceptions.LoginFailuresListener;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.JavaScriptObject;

public class JsLoginResult implements JsBaseResult<JsLoginResult>, Exportable {

	JsResult jsResult;
	LoginResult loginResult;

	@NoExport
	public void setJsResult(final JsResult result) {
		this.jsResult = result;
	}

	@NoExport
	public void setLoginResult(final LoginResult loginResult) {
		this.loginResult = loginResult;
	}

	@NoExport
	@Override
	public BasicResult<Object> getOriginal() {
		return jsResult.getOriginal();
	}

	@NoExport
	@Override
	public void setOriginal(final BasicResult<Object> original) {
		jsResult.setOriginal(original);
	}

	@NoExport
	public static JsLoginResult wrap(final LoginResult loginResult,
			final Session session) {
		final JsLoginResult jsResult = new JsLoginResult();

		jsResult.setLoginResult(loginResult);
		jsResult.setJsResult(JH.jsFactory(session).createResult(loginResult));

		return jsResult;
	}

	public JsLoginResult() {
		super();
	}

	@Export
	public JsLoginResult catchLoginFailures(final JavaScriptObject params) {

		final LoginFailuresParameters loginParams = params.cast();

		final LoginFailuresListener listener = new LoginFailuresListener() {

			@Override
			public void onUserAlreadyRegistered() {
				loginParams.triggerUserAlreadyRegistered();
			}

			@Override
			public void onNotRegisteredForApplication() {
				loginParams.triggerUserNotRegisteredForApplication();
			}

			@Override
			public void onInvalidDetails() {
				loginParams.triggerInvalidDetails();
			}

			@Override
			public void onChallenged(final ChallengedResult cr) {
				loginParams.triggerOnChallenged(cr.challengeLink().getUri(), cr
						.challengeLink().getSecret());
			}
		};

		loginResult.catchLoginFailures(listener);

		return this;

	}

	/*
	 * Base result operations
	 */

	@Export
	@Override
	public JsLoginResult catchExceptions(final JsClosure exceptionListener) {
		jsResult.catchExceptions(exceptionListener);
		return this;
	}

	@Export
	@Override
	public JsLoginResult catchUndefined(final JsClosure undefinedListener) {
		jsResult.catchUndefined(undefinedListener);
		return this;
	}

	@Export
	@Override
	public JsLoginResult catchUnauthorized(final JsClosure unauthorizedListener) {
		jsResult.catchUnauthorized(unauthorizedListener);
		return this;
	}

	@Export
	@Override
	public JsLoginResult catchImpossible(final JsClosure impossibleListener) {
		jsResult.catchImpossible(impossibleListener);
		return this;
	}

	@Export
	@Override
	public Object get(final Object... params) {
		return jsResult.get(params);
	}

}
