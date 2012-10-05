package io.nextweb.tests.onedb;

import io.nextweb.Session;
import io.nextweb.common.LoginResult;
import io.nextweb.common.User;
import io.nextweb.engine.NextwebEngine;

import org.junit.Assert;
import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbJre;

public class TestLogin {

	public Session createSession() {
		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		return session;
	}

	@Test
	public void testLoginAtApplication() {

		final Session session = createSession();

		final LoginResult login = session.login("testloginuser@linnk.de",
				"123456",
				session.node("https://u1.linnk.it/0fs7dr/Apps1/appjangle"));

		final User user = login.get();

		Assert.assertTrue(user.sessionToken() != null);
		Assert.assertTrue(user.userNode() != null);
		Assert.assertTrue(user.email() != null);

	}

}
