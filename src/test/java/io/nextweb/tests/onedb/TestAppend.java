package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;

public class TestAppend {

	public Session getSession() {
		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		return session;
	}

	@Test
	public void testNodeAppend() throws InterruptedException {

		String testNode = "http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Further_Append_Tests";
		String testNodeSecret = "ChaiK3CZYnrr";

		Session session = getSession();

		Link link = session.node(testNode, testNodeSecret);

		Node node = link.get();

		Node testAppend = node.append("Appending");
		Node nested = testAppend.append("Nested");

		session.commit().get();

		Result<Success> removeNested = testAppend.remove(nested);
		Result<Success> removeNode = node.remove(testAppend);

		session.getAll(removeNested, removeNode);

		Result<Integer> clearVersions = node.clearVersions(2);

		clearVersions.get();

		session.close().get();

	}
}
