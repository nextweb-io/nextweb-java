package io.nextweb.tests.entity;

import io.nextweb.Query;
import io.nextweb.Session;

import org.junit.Test;

import com.ononedb.nextweb.jre.Onedb;
import com.ononedb.nextweb.local.OnedbLocalDb;
import com.ononedb.nextweb.local.jre.OnedbLocalDbJre;

/**
 * In this test case a new seed node is requested and a node with the text
 * "Hello, World!" is then appended to this seed node.
 * 
 * @author <a href="http://www.mxro.de/">Max Rohde</a>
 * 
 */
public class TestAppend {

	@Test
	public void testAppend() {

		final OnedbLocalDb localDb = OnedbLocalDbJre.init(21332);
		final Session session = Onedb.init().createSession();

		final Query node = session.seed("local");

		node.append("Hello, Wolrd!");

		session.commit().get();

		session.close().get();
		localDb.shutdown().get();
	}

}
