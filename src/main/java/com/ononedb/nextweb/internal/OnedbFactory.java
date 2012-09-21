package com.ononedb.nextweb.internal;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.fn.AsyncResult;
import io.nextweb.operations.exceptions.ExceptionManager;

import java.util.List;

import one.core.domain.OneClient;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.OnedbLink;
import com.ononedb.nextweb.OnedbLinkList;
import com.ononedb.nextweb.OnedbLinkListQuery;
import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.OnedbNode;
import com.ononedb.nextweb.OnedbNodeList;
import com.ononedb.nextweb.OnedbNodeListQuery;
import com.ononedb.nextweb.OnedbQuery;
import com.ononedb.nextweb.OnedbSession;

public class OnedbFactory {

	public final OnedbQuery createQuery(OnedbSession session,
			ExceptionManager fallbackExceptionManager,
			AsyncResult<Node> asyncResult) {
		return new OnedbQuery(session, fallbackExceptionManager, session
				.getEngine().createResult(asyncResult));
	}

	public final OnedbLink createLink(OnedbSession session,
			ExceptionManager fallbackExceptionManager, String uri) {
		return new OnedbLink(session, fallbackExceptionManager, uri);

	}

	public final OnedbNodeListQuery createNodeListQuery(OnedbSession session,
			ExceptionManager fallbackExceptionManager,
			AsyncResult<NodeList> result) {
		return new OnedbNodeListQuery(session, fallbackExceptionManager,
				session.getEngine().createResult(result));
	}

	public final OnedbNodeList createNodeList(OnedbSession session,
			ExceptionManager fallbackExceptionManager, List<Node> list) {
		return new OnedbNodeList(session, fallbackExceptionManager, list);
	}

	public final OnedbLinkList createLinkList(OnedbSession session,
			ExceptionManager fallbackExceptionManager, List<Link> list) {
		return new OnedbLinkList(session, fallbackExceptionManager, list);
	}

	public final OnedbLinkListQuery createLinkListQuery(OnedbSession session,
			ExceptionManager fallbackExceptionManager,
			AsyncResult<LinkList> result) {
		return new OnedbLinkListQuery(session, session.getEngine()
				.createResult(result), fallbackExceptionManager);
	}

	public final OnedbNode createNode(OnedbSession session,
			ExceptionManager fallbackExceptionManager, OneTypedReference<?> node) {
		return new OnedbNode(session, fallbackExceptionManager, node);
	}

	public final OnedbSession createSession(OnedbNextwebEngine engine,
			ExceptionManager fallbackExceptionManager, OneClient client) {
		return new OnedbSession(engine, fallbackExceptionManager, client);
	}

}
