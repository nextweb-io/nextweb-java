package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;

public class TestAppend {

	public Session getSession() {
		final NextwebEngine engine = OnedbNextwebJreEngine.init();

		final Session session = engine.createSession();

		return session;
	}

	@Test
	public void testNodeAppend() throws InterruptedException {

		final String testNode = "http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Further_Append_Tests";
		final String testNodeSecret = "ChaiK3CZYnrr";

		final Session session = getSession();

		final Link link = session.node(testNode, testNodeSecret);

		final Node node = link.get();

		final Query testAppend = node.append("Appended");
		final Query nested = testAppend.append("Nested");

		session.getAll(testAppend, nested); // should be executed in REAL TIME

		session.commit().get();

		final Result<Success> removeNested = testAppend.remove(nested);
		final Result<Success> removeNode = node.remove(testAppend);

		session.getAll(removeNested, removeNode);

		final Result<Integer> clearVersions = node.clearVersions(2);

		clearVersions.get();

		session.close().get();

	}

	@Test
	public void testNodeAppendSafe() throws InterruptedException {

		final String testNode = "http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Further_Append_Tests";
		final String testNodeSecret = "ChaiK3CZYnrr";

		final Session session = getSession();

		final Link link = session.node(testNode, testNodeSecret);

		final Node node = link.get();

		final Query testAppend = node.appendSafe("Appended");
		final Query nested = testAppend.appendSafe("Nested");

		// testAppend.get();
		// System.out.println("Appended first!");
		// session.commit().get();

		session.getAll(testAppend, nested);

		final Result<Success> removeNested = testAppend.remove(nested);
		final Result<Success> removeNode = node.remove(testAppend);

		session.getAll(removeNested, removeNode);

		final Result<Integer> clearVersions = node.clearVersions(2);

		clearVersions.get();

		session.close().get();

	}
}
