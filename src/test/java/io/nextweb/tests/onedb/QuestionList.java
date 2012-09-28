package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;

import com.ononedb.nextweb.jre.OnedbNextwebJreEngine;

public class QuestionList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NextwebEngine engine = OnedbNextwebJreEngine.init();

		Session session = engine.createSession();

		Link questions = session
				.node("http://slicnet.com/seed1/seed1/1/0/3/h/sd/questions");

	}

}
