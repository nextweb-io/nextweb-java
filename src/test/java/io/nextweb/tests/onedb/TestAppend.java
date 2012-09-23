package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;

public class TestAppend {

	public Session getSession() {
		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		return session;
	}

	@Test
	public void testNodeAppend() {

		String testNode = "http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Append_Tests";
		String testNodeSecret = "VeoahgE5ixO4";

		Session session = getSession();

		Link link = session.node(testNode, testNodeSecret);

		Node node = link.get();

		// node.append("Hello, world.");

		session.commit();

		session.close().get();

	}
}
