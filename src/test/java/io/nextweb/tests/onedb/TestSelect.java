package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.operations.exceptions.UndefinedListener;
import io.nextweb.operations.exceptions.UndefinedResult;
import junit.framework.Assert;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;

public class TestSelect {

	@Test
	public void testSelect() {

		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		Query questionBagQuery = questionBagRepository.select(aQuestionBag);

		Node questionBag = questionBagQuery.get();

		Assert.assertTrue(questionBag.getValue() != null);

		session.close().get();

	}

	@Test
	public void testParallelSelect() {

		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		Query questionBagQuery = questionBagRepository.select(aQuestionBag);

		session.getAll(questionBagRepository, aQuestionBag, questionBagQuery);

		Node questionBag = questionBagQuery.get();

		Assert.assertTrue(questionBag.getValue() != null);

		session.close().get();

	}

	@Test
	public void testSelectPlugin() {
		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		Query questionBagQuery = questionBagRepository.plugin(
				engine.plugin().select()).select(aQuestionBag);

		session.getAll(questionBagRepository, aQuestionBag, questionBagQuery);

		Node questionBag = questionBagQuery.get();

		Assert.assertTrue(questionBag.getValue() != null);

		session.close().get();
	}

	@Test
	public void testSelectAll() {
		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		Link questions = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd/questions");

		NodeListQuery allQuestionsQuery = questions.selectAll();

		NodeList allQuestions = allQuestionsQuery.get();

		allQuestions.each(new Closure<Node>() {

			@Override
			public void apply(Node o) {

			}
		});

		Assert.assertTrue(allQuestions.size() > 0);

	}

	@Test
	public void testSelectNested() {
		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		Link aBrandName = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Brand_Name");

		Link aQuestion = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Strategy_Quadrant_Questi");

		Link questions = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd/questions");

		questions.catchUndefined(new UndefinedListener() {

			@Override
			public void onUndefined(UndefinedResult r) {
				System.out.println("Undefined!");
				throw new RuntimeException("E");
			}
		});

		NodeList resolvedQuestions = questions.selectAll().get();

		resolvedQuestions.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				// System.out.println("Exception intercepted: "
				// + t.getLocalizedMessage());
			}
		});

		resolvedQuestions.select(aBrandName).get(new Closure<NodeList>() {

			@Override
			public void apply(NodeList result) {
				// System.out.println("success");
			}

		});

		// System.out.println(allBrandNames);

		session.close().get();

	}
}
