package io.nextweb.tests.onedb;

import io.nextweb.Link;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;

import org.junit.Test;

import com.ononedb.nextweb.jre.OnedbNextwebEngineJre;

public class TestDownloadPostbox {

	public Session getSession() {
		final NextwebEngine engine = OnedbNextwebEngineJre.init();

		final Session session = engine.createSession();

		return session;
	}

	@Test
	public void testDownloadPostbox() {

		final Session session = getSession();

		final Link pendingQuestionsNode = session.node(
				"http://slicnet.com/questio/questio", "thd3pb41jrke83i");

		pendingQuestionsNode.selectAll().get();

		session.close().get();

	}

}
