package io.nextweb.tests.entity;

import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbJre;

/**
 * In this test case a new seed node is requested and a node with the text
 * "Hello, World!" is then appended to this seed node.
 * 
 * @author Max Rohde
 * 
 */
public class TestAppend {

	public Session createSession() {
		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		return session;
	}

	@Test
	public void testAppend() {
		final Session session = createSession();
		final Query node = session.seed("local");

		node.append("Hello, Wolrd!");

		session.commit().get();
		session.close().get();
	}

}
