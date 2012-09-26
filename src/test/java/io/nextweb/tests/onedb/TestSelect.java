package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Closure;
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

		ListQuery allQuestionsQuery = questions.selectAll();

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

		Link isQuestion = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Strategy_Quadrant_Questi");

		Link isBrandName = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Brand_Name");

		Link questions = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd/questions");

		NodeList brandNames = questions.selectAll(isQuestion)
				.select(isBrandName).get();

		System.out.println("Brand names: " + brandNames.values());

		session.close().get();

	}
}
