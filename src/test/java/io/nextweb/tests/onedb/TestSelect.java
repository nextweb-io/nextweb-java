package io.nextweb.tests.onedb;

import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;

public class TestSelect {

	@Test
	public void testSelect() {

		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		session.load("http://slicnet.com/seed1/seed1/9/1/h/sd");

		session.close().get();

	}

}
