package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;

import org.junit.Assert;
import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebEngineJre;

public class TestExceptions {

	public Session getSession() {
		NextwebEngine engine = OnedbNextwebEngineJre.init();

		Session session = engine.createSession();

		return session;
	}

	@Test
	public void testSessionUndefinedCatching() {

		Session session = getSession();

		session.getExceptionManager().catchUndefined(new UndefinedListener() {

			@Override
			public void onUndefined(UndefinedResult r) {
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
	public void testSessionExceptionCatching() {

		Session session = getSession();

		session.getExceptionManager().catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(ExceptionResult r) {
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
			public void onFailure(ExceptionResult r) {
				Assert.assertTrue(
						"Exception should not have been intercepted.", false);
			}
		});

		Link node = session.node("http://slicnet.com/IdoNotExist/atRealm");

		node.catchUndefined(new UndefinedListener() {

			@Override
			public void onUndefined(UndefinedResult r) {
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
			public void onFailure(ExceptionResult r) {
				Assert.assertTrue("Successfully intercepted", true);
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
	public void testNestedExceptionCastingDepth() {
		Session session = getSession();

		Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		Link aStartTime = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/1/n/Web_Time_Reporter_Docume/Types/Start_Time");

		questionBagRepository.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(ExceptionResult r) {
				Assert.assertTrue("Exception forwarded too far", false);
			}
		});

		Query questionBag = questionBagRepository.select(aQuestionBag);

		questionBag.catchUndefined(new UndefinedListener() {

			@Override
			public void onUndefined(UndefinedResult r) {
				Assert.assertTrue("Successfully intercepted", true);
			}
		});

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

		Session session = getSession();
		Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		Link doesNotExist = session
				.node("http://slicnet.com/IdoNotExist/atRealm");

		doesNotExist.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(ExceptionResult r) {
				Assert.assertTrue(
						"Exception should not have been intercepted.", false);
			}
		});

		try {
			session.getAll(questionBagRepository, aQuestionBag, doesNotExist);
		} catch (Throwable t) {
			Assert.assertTrue("Exception successfully thrown", true);
			session.close().get();
			return;
		}
		Assert.assertTrue("Exception should have been thrown.", false);

	}
}
