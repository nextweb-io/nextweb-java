package io.nextweb.tests.entity;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ononedb.nextweb.local.OnedbLocalServer;
import com.ononedb.nextweb.local.jre.OnedbStartServerJre;

/**
 * In this test case a new seed node is requested and a node with the text
 * "Hello, World!" is then appended to this seed node.
 * 
 * @author <a href="http://www.mxro.de/">Max Rohde</a>
 * 
 */
public class TestAppend {

	OnedbLocalServer localDb;
	/**
	 * Session to perform operation.
	 */
	Session session;
	/**
	 * Session for asserting correct execution of operation.
	 */
	Session session2;

	/**
	 * Query for the node to perform test of operation on.
	 */
	Query query;

	/**
	 * Link to the node to perform test of operation on.
	 */
	Link link;

	/**
	 * Node to perform test of operation on.
	 */
	Node node;

	@Before
	public void setUp() {
		localDb = OnedbStartServerJre.newInstance(21332);
		session = localDb.createSession();
		session2 = localDb.createSession();

		query = session.seed("local");
		node = query.get();
		link = session.node(node);
	}

	@Test
	public void testAppendToQuery() {
		// Append a node containing the text "Hello, World!" to the root node of
		// this test.
		query.append("Hello, World!");

		// Synchronize changes between session and (local) server.
		session.commit().get();

		assertHelloWorldAppended();
	}

	@Test
	public void testAppendToLink() {
		// Append a node containing the text "Hello, World!" to the root node of
		// this test.
		link.append("Hello, World!");

		// Synchronize changes between session and (local) server.
		session.commit().get();

		assertHelloWorldAppended();
	}

	@Test
	public void testAppendToNode() {
		// Append a node containing the text "Hello, World!" to the root node of
		// this test.
		node.append("Hello, World!");

		// Synchronize changes between session and (local) server.
		session.commit().get();

		assertHelloWorldAppended();
	}

	/**
	 * Assert that the text representation of all children contains the text
	 * "Hello, World!".
	 */
	private void assertHelloWorldAppended() {
		Assert.assertTrue(session2.node(query.get()).selectAll().get()
				.as(String.class).contains("Hello, World!"));
	}

	@After
	public void tearDown() {
		// Close first session.
		session.close().get();
		// Close second session.
		session2.close().get();
		// Stopping local test server.
		localDb.shutdown().get();
	}
}
