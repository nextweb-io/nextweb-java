package com.ononedb.nextweb.internal;

import com.ononedb.nextweb.OnedbLink;
import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.OnedbNode;
import com.ononedb.nextweb.OnedbQuery;
import com.ononedb.nextweb.OnedbSession;

import io.nextweb.Node;
import io.nextweb.fn.AsyncResult;
import io.nextweb.operations.exceptions.ExceptionManager;
import one.core.domain.OneClient;
import one.core.nodes.OneTypedReference;


public class OnedbFactory {

	public OnedbQuery createQuery(OnedbSession session,
			ExceptionManager fallbackExceptionManager,
			AsyncResult<Node> asyncResult) {
		return new OnedbQuery(session, fallbackExceptionManager, session
				.getEngine().createResult(asyncResult));
	}

	public OnedbLink createLink(OnedbSession session,
			ExceptionManager fallbackExceptionManager, String uri) {
		return new OnedbLink(session, fallbackExceptionManager, uri);

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
