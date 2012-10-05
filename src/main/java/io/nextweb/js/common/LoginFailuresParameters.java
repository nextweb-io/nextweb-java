package io.nextweb.js.common;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginFailuresParameters extends JavaScriptObject {

	public final native void triggerUserAlreadyRegistered()/*-{
															this.onUserAlreadyRegistered();
															}-*/;

	public final native void triggerUserNotRegisteredForApplication()/*-{ 
																		this.onUserNotRegisteredForApplication();
																		}-*/;

	public final native void triggerInvalidDetails()/*-{ this.onInvalidDetails(); }-*/;

	public final native void triggerOnChallenged(String challengeNodeUri,
			String challengeNodeSecret)/*-{
										this.onChallenged(challengeNodeUri, challengeNodeSecret);
										}-*/;

	protected LoginFailuresParameters() {
	};

}
