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

	// @Test
	// public void testClearVersions() {
	// final CoreDsl dsl = OneJre.init();
	//
	// final OneClient client = dsl.createClient();
	//
	// dsl.load(
	// "http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Further_Append_Tests")
	// .withSecret("ChaiK3CZYnrr").and(new WhenLoaded() {
	//
	// @Override
	// public void thenDo(WithLoadResult<Object> arg0) {
	// System.out.println("Loaded!");
	// dsl.clearVersions(arg0.loadedNode()).andKeepOnServer(1)
	// .in(client).and(new WhenVersionsCleared() {
	//
	// @Override
	// public void thenDo(
	// WithVersionsClearedResult arg0) {
	// System.out.println("done");
	// }
	//
	// });
	// }
	// });
	//
	// try {
	// Thread.sleep(15000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Test
	public void testNodeAppend() throws InterruptedException {

		String testNode = "http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Further_Append_Tests";
		String testNodeSecret = "ChaiK3CZYnrr";

		Session session = getSession();

		Link link = session.node(testNode, testNodeSecret);

		Node node = link.get();

		Node testAppend = node.append("Appending");
		// System.out.println("appended");

		Node nested = testAppend.append("Nested");

		session.commit().get();

		Result<Success> removeNested = testAppend.remove(nested);
		Result<Success> removeNode = node.remove(testAppend);

		session.getAll(removeNested, removeNode);

		// System.out.println("All removed.");

		// System.out.println(node.clearVersions(2).get());

		// Thread.sleep(5000);

		Result<Integer> clearVersions = node.clearVersions(2);

		clearVersions.get();

		session.close().get();

	}
}
