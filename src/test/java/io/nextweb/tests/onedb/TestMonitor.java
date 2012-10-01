package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Session;
import io.nextweb.common.Interval;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Closure;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbJre;

public class TestMonitor {

	public Session getSession() {
		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		return session;
	}

	@Test
	public void testMonitor() throws InterruptedException {

		final String testNodeUri = "http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Further_Append_Tests";
		final String testNodeSecret = "ChaiK3CZYnrr";

		final Session session = getSession();

		final Link testNode = session.node(testNodeUri, testNodeSecret);

		final CountDownLatch latch = new CountDownLatch(1);

		testNode.monitor(Interval.REAL_TIME, new Closure<Node>() {

			@Override
			public void apply(final Node o) {
				// System.out.println("Change detected!");
				latch.countDown();
			}
		});

		final Session session2 = getSession();

		final Link testNode2 = session.node(testNodeUri, testNodeSecret);

		testNode2
				.remove(session
						.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/7/n/Further_Append_Tests/Monitor70"));

		testNode2.append("Monitor");

		session2.commit().get();

		latch.await();

	}
}
