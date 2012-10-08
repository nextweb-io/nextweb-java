package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.common.LocalServer;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.jre.Nextweb;

import org.junit.Assert;
import org.junit.Test;

public class TestLocalJre {

	@Test
	public void testLocalSeed() {

		final NextwebEngine engine = Nextweb.getEngine();

		final Session session = engine.createSession();

		Nextweb.startServer(10021);

		final Node root = session.seed("local").get();

		final Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");
		root.append("one").append(aQuestionBag);
		root.append("two");
		root.append("three");

		session.commit().get();

		aQuestionBag.get();

		Assert.assertTrue(root.selectAll().get().size() > 2);

	}

	@Test
	public void testLocalSeed2() {

		final NextwebEngine engine = Nextweb.getEngine();

		final Session session = engine.createSession();

		Nextweb.startServer(10022);

		final Node root = session.seed("local").get();

		root.append("one");
		root.append("two");
		root.append("three");

		session.commit().get();

		Assert.assertTrue(root.selectAll().get().size() > 2);
	}

	@Test
	public void testLocalCreateRealm() {
		final NextwebEngine engine = Nextweb.getEngine();

		final LocalServer localServer = Nextweb.startServer(10023);

		final Session session = engine.createSession();

		final Query localRealm = session.createRealm("test", "local", "");

		final Node realmNode = localRealm.get();

		final Session session2 = engine.createSession();

		Assert.assertTrue(session2.node(realmNode.getUri(),
				realmNode.getSecret()).get() != null);

		session.close().get();

		session2.close().get();

		localServer.shutdown().get();

	}
}
