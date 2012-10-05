package io.nextweb.tests.onedb;

import io.nextweb.Entity;
import io.nextweb.Node;
import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Closure;

import org.junit.Test;

import com.ononedb.nextweb.jre.Onedb;

public class TestSetValue {

	public Session getSession() {
		NextwebEngine engine = Onedb.init();

		Session session = engine.createSession();

		return session;
	}

	@Test
	public void testSetValue() {

		Session session = getSession();

		Entity testNode = session
				.node("http://slicnet.com/seed1/seed1/1/2/9/h/sd/nj/Basic_Test_Cases_Data",
						"uS4aS5MoQeEE");

		testNode.setValue("# Hello, I've been here").get(new Closure<Node>() {

			@Override
			public void apply(Node o) {

				o.setValue("And I'm there, too!");
			}

		});

		// Thread.sleep(2000);

		session.close().get();

	}

	@Test
	public void testSetValueSafe() {
		Session session = getSession();

		Entity testNode = session
				.node("http://slicnet.com/mxrogm/mxrogm/apps/nodejump/docs/1/9/n/Test_Set_Value_Safe",
						"hWB9knYJFx2j");

		testNode.setValueSafe("# SET VALUE\n\n").get(new Closure<Node>() {

			@Override
			public void apply(Node o) {
				System.out.println("set completed");

			}
		});

		session.close().get();

	}

}
