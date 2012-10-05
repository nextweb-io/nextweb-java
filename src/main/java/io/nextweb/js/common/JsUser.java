package io.nextweb.js.common;

import io.nextweb.common.User;
import io.nextweb.js.JsLink;
import io.nextweb.js.JsWrapper;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

public class JsUser implements Exportable, JsWrapper<User> {

	User original;

	@Export
	public String email() {
		return original.email();
	}

	@Export
	public String sessionToken() {
		return original.sessionToken();
	}

	@Export
	public JsLink userNode() {
		return JH.jsFactory(original.session()).createLink(original.userNode());
	}

	@NoExport
	@Override
	public User getOriginal() {
		return original;
	}

	@NoExport
	@Override
	public void setOriginal(final User original) {
		this.original = original;
	}

	public JsUser() {
		super();
	}

	@NoExport
	public static JsUser wrap(final User user) {
		final JsUser jsUser = new JsUser();
		jsUser.setOriginal(user);
		return jsUser;
	}

}
