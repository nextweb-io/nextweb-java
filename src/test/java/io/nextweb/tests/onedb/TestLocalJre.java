package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;

import org.junit.Assert;
import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;
import com.ononedb.nextweb.local.jre.OnedbNextwebLocalJre;

public class TestLocalJre {

	@Test
	public void testLocalSeed() {

		final NextwebEngine engine = OnedbNextwebJreEngine.init();

		final Session session = engine.createSession();

		OnedbNextwebLocalJre.init(10021);

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

		final NextwebEngine engine = OnedbNextwebJreEngine.init();

		final Session session = engine.createSession();

		OnedbNextwebLocalJre.init(10022);

		final Node root = session.seed("local").get();

		root.append("one");
		root.append("two");
		root.append("three");

		session.commit().get();

		Assert.assertTrue(root.selectAll().get().size() > 2);
	}

	@Test
	public void testLocalCreateRealm() {
		final NextwebEngine engine = OnedbNextwebJreEngine.init();

		final Session session = engine.createSession();

		OnedbNextwebLocalJre.init(10023);

		final Query localRealm = session.createRealm("test", "local", "");

		System.out.println(localRealm.get().getUri() + " "
				+ localRealm.get().getSecret());
	}

}
