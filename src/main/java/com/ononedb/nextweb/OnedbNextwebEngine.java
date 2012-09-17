package com.ononedb.nextweb;

import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;
import one.client.jre.OneJre;
import one.core.dsl.CoreDsl;

public class OnedbNextwebEngine implements NextwebEngine {

	private CoreDsl dsl;

	@Override
	public Session createSession() {

		if (dsl == null) {
			dsl = OneJre.init();
		}

		return null;
	}

}
