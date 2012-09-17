package com.ononedb.nextweb.jre;

import com.ononedb.nextweb.OnedbSession;

import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import one.client.jre.OneJre;
import one.core.dsl.CoreDsl;

public class OnedbNextwebJreEngine implements NextwebEngine {

	private CoreDsl dsl;

	@Override
	public Session createSession() {

		if (dsl == null) {
			dsl = OneJre.init();
		}

		return new OnedbSession(dsl.createClient());
	}

	@Override
	public void unhandledException(Object context, Throwable t) {
		throw new RuntimeException(t);
	}

}
