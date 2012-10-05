package com.ononedb.nextweb.internal;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.BooleanResult;
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

	public final OnedbQuery createQuery(final OnedbSession session,
			final ExceptionManager fallbackExceptionManager,
			final AsyncResult<Node> asyncResult) {
		return new OnedbQuery(session, fallbackExceptionManager, session
				.getEngine().createResult(fallbackExceptionManager, session,
						asyncResult));
	}

	public final OnedbLink createLink(final OnedbSession session,
			final ExceptionManager fallbackExceptionManager, final String uri,
			final String secret) {
		return new OnedbLink(session, fallbackExceptionManager, uri, secret);

	}

	public final OnedbNodeListQuery createNodeListQuery(
			final OnedbSession session,
			final ExceptionManager fallbackExceptionManager,
			final AsyncResult<NodeList> result) {
		return new OnedbNodeListQuery(session, fallbackExceptionManager,
				session.getEngine().createResult(fallbackExceptionManager,
						session, result));
	}

	public final BooleanResult createBooleanResult(
			final ExceptionManager exceptionManager, final Session session,
			final AsyncResult<Boolean> result) {
		return new BooleanResult(exceptionManager, session, result);
	}

	public final OnedbNodeList createNodeList(final OnedbSession session,
			final ExceptionManager fallbackExceptionManager,
			final List<Node> list) {
		return new OnedbNodeList(session, fallbackExceptionManager, list);
	}

	public final OnedbLinkList createLinkList(final OnedbSession session,
			final ExceptionManager fallbackExceptionManager,
			final List<Link> list) {
		return new OnedbLinkList(session, fallbackExceptionManager, list);
	}

	public final OnedbLinkListQuery createLinkListQuery(
			final OnedbSession session,
			final ExceptionManager fallbackExceptionManager,
			final AsyncResult<LinkList> result) {
		return new OnedbLinkListQuery(session, session.getEngine()
				.createResult(fallbackExceptionManager, session, result),
				fallbackExceptionManager);
	}

	public final OnedbNode createNode(final OnedbSession session,
			final ExceptionManager fallbackExceptionManager,
			final OneTypedReference<?> node, final String secret) {
		return new OnedbNode(session, fallbackExceptionManager, node, secret);
	}

	public final OnedbSession createSession(final OnedbNextwebEngine engine,
			final OneClient client) {
		return new OnedbSession(engine, client);
	}

	public final ExceptionManager createExceptionManager(
			final ExceptionManager parentExceptionManager) {
		return new ExceptionManager(parentExceptionManager);
	}

}
