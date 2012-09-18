package com.ononedb.nextweb.common;

import io.nextweb.Node;
import io.nextweb.fn.Result;
import io.nextweb.operations.exceptions.ExceptionManager;
import one.core.domain.OneClient;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.OnedbSession;

public class OnedbFactory {

	public OnedbQuery createQuery(OnedbSession session,
			ExceptionManager fallbackExceptionManager, Result<Node> result) {
		return new OnedbQuery(session, fallbackExceptionManager, result);
	}

	public OnedbNode createNode(OnedbSession session,
			ExceptionManager fallbackExceptionManager, OneTypedReference<?> node) {
		return new OnedbNode(session, fallbackExceptionManager, node);
	}

	public OnedbSession createSession(OnedbNextwebEngine engine,
			ExceptionManager fallbackExceptionManager, OneClient client) {
		return new OnedbSession(engine, fallbackExceptionManager, client);
	}

}
