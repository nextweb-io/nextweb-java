package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.exceptions.UndefinedExceptionListener;

import org.junit.Assert;
import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;

public class TestExceptions {

	public Session getSession() {
		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		return session;
	}

	@Test
	public void testSessionUndefinedCatching() {

		Session session = getSession();

		session.getExceptionManager().catchUndefinedExceptions(
				new UndefinedExceptionListener() {

					@Override
					public void onUndefined(Object origin, String message) {
						Assert.assertTrue(
								"Exception successfully intercepted.", true);
					}
				});

		session.node("http://slicnet.com/IdoNotExist/atRealm").get(
				new Closure<Node>() {

					@Override
					public void apply(Node o) {
						Assert.assertTrue("Should not be loaded.", false);
					}

				});

		session.close().get();
	}

	@Test
	public void testSessionExceptionCatching() {

		Session session = getSession();

		session.getExceptionManager().catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				Assert.assertTrue("Exception successfully intercepted.", true);
			}
		});

		session.node("http://slicnet.com/IdoNotExist/atRealm").get(
				new Closure<Node>() {

					@Override
					public void apply(Node o) {
						Assert.assertTrue("Should not be loaded.", false);
					}

				});

		session.close().get();
	}

	@Test
	public void testSynchronousExceptionThrowing() {

		Session session = getSession();

		session.getExceptionManager().catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				Assert.assertTrue(
						"Exception should not have been intercepted.", false);
			}
		});

		Link node = session.node("http://slicnet.com/IdoNotExist/atRealm");

		node.catchUndefinedExceptions(new UndefinedExceptionListener() {

			@Override
			public void onUndefined(Object origin, String message) {
				Assert.assertTrue(
						"Exception should not have been intercepted.", false);
			}
		});

		try {
			Node n = node.get();
			Assert.assertTrue("Expected exception not cast. Node resolved to "
					+ n, false);
		} catch (Throwable t) {
			Assert.assertTrue("Expected cast successfully.", true);
			session.close().get();
			return;
		}

		Assert.assertTrue("Expected exception not cast", false);
	}

	@Test
	public void testNestedExceptionCasting() {
		Session session = getSession();

		Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		Link aStartTime = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/1/n/Web_Time_Reporter_Docume/Types/Start_Time");

		questionBagRepository.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				t.printStackTrace();
				System.out.println("Successfully intercepted");
			}
		});

		Query questionBag = questionBagRepository.select(aQuestionBag);

		questionBag.select(aStartTime).get(new Closure<Node>() {

			@Override
			public void apply(Node o) {
				Assert.assertTrue("Node should not be available.", false);
			}

		});

		session.close().get();

	}

	@Test
	public void testMultipleExceptions() {
		// get all ...
	}

}
