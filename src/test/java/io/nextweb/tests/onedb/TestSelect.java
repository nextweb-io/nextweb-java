package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Closure;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.core.Plugin_Entity_Select;

import org.junit.Assert;
import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbJre;
import com.ononedb.nextweb.plugins.P_Entity_Select_Factory;

public class TestSelect {

	@Test
	public void testSelect() {

		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		final Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		final Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		final Query questionBagQuery = questionBagRepository
				.select(aQuestionBag);

		final Node questionBag = questionBagQuery.get();

		Assert.assertTrue(questionBag.getValue() != null);

		session.close().get();

	}

	@Test
	public void testParallelSelect() {

		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		final Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		final Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		final Query questionBagQuery = questionBagRepository
				.select(aQuestionBag);

		session.getAll(questionBagRepository, aQuestionBag, questionBagQuery);

		final Node questionBag = questionBagQuery.get();

		Assert.assertTrue(questionBag.getValue() != null);

		session.close().get();

	}

	@SuppressWarnings("unchecked")
	public <GPlugin extends Plugin_Entity_Select<?>> PluginFactory<?, GPlugin> select() {
		final Object factory = P_Entity_Select_Factory.FACTORY;
		return (PluginFactory<?, GPlugin>) factory;
	}

	@Test
	public void testSelectPlugin() {
		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		final Link questionBagRepository = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd");

		final Link aQuestionBag = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Question_Bag");

		final Query questionBagQuery = questionBagRepository.plugin(select())
				.select(aQuestionBag);
		// final Query questionBagQuery = questionBagRepository
		// .select(aQuestionBag);

		session.getAll(questionBagRepository, aQuestionBag, questionBagQuery);

		final Node questionBag = questionBagQuery.get();

		Assert.assertTrue(questionBag.getValue() != null);

		session.close().get();
	}

	@Test
	public void testSelectAll() {
		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		final Link questions = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd/questions");

		final ListQuery allQuestionsQuery = questions.selectAll();

		final NodeList allQuestions = allQuestionsQuery.get();

		allQuestions.each(new Closure<Node>() {

			@Override
			public void apply(final Node o) {

			}
		});

		Assert.assertTrue(allQuestions.size() > 0);

	}

	@Test
	public void testSelectNested() {
		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		final Link isQuestion = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Strategy_Quadrant_Questi");

		final Link isBrandName = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Brand_Name");

		final Link questions = session
				.node("http://slicnet.com/seed1/seed1/9/1/h/sd/questions");

		final NodeList brandNames = questions.selectAll(isQuestion)
				.select(isBrandName).get();

		System.out.println("Brand names: " + brandNames.values());

		session.close().get();

	}

	@Test
	public void testHas() {
		final NextwebEngine engine = OnedbJre.init();

		final Session session = engine.createSession();

		final Link isQuestion = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Strategy_Quadrant_Questi");

		final Link isBrandName = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Brand_Name");

		final Link isBrandImage = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/8/n/Types/Brand_Image");

		final Link question = session
				.node("http://slicnet.com/seed1/seed1/7/0/h/sd/q");

		Assert.assertTrue(question.has(isBrandName)
				.and(question.has(isBrandImage)).get());
		Assert.assertFalse(question.has(isQuestion).get());

	}
}
