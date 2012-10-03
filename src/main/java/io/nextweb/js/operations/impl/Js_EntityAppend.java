package io.nextweb.js.operations.impl;

import io.nextweb.Entity;
import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.js.JsLink;
import io.nextweb.js.JsNode;
import io.nextweb.js.JsQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.operations.JsEntityAppendOperations;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.JavaScriptObject;

public class Js_EntityAppend implements JsEntityAppendOperations {

	private final Entity node;

	private static final Node getNode(final Object javaValue) {
		return ((JsNode) javaValue).getOriginal();
	}

	private static final Link getLink(final Object javaValue) {
		return ((JsLink) javaValue).getOriginal();
	}

	private final static Query getQuery(final Object javaValue) {
		return ((JsQuery) javaValue).getOriginal();
	}

	private JavaScriptObject createQuery(final Query query) {
		return ExporterUtil.wrap(JH.jsFactory(node).createQuery(query));
	}

	public Js_EntityAppend(final Entity node) {
		super();
		this.node = node;
	}

	@Override
	public Object append(final Object value) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.append(getQuery(javaValue));
		} else if (javaValue instanceof JsLink) {
			query = node.append(getLink(javaValue));
		} else if (javaValue instanceof JsNode) {
			query = node.append(getNode(javaValue));
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.append(javaValue);
		}

		return createQuery(query);
	}

	@Override
	public Object append(final Object value, final String atAddress) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.append(getQuery(javaValue), atAddress);
		} else if (javaValue instanceof JsLink) {
			query = node.append(getLink(javaValue), atAddress);
		} else if (javaValue instanceof JsNode) {
			query = node.append(getNode(javaValue), atAddress);
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.append(javaValue, atAddress);
		}

		return createQuery(query);
	}

	@Override
	public Object appendValue(final Object value) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query = node.appendValue(javaValue);

		return createQuery(query);
	}

	@Override
	public Object appendSafe(final Object value) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.appendSafe(getQuery(javaValue));
		} else if (javaValue instanceof JsLink) {
			query = node.appendSafe(getLink(javaValue));
		} else if (javaValue instanceof JsNode) {
			query = node.appendSafe(getNode(javaValue));
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.appendSafe(javaValue);
		}

		return createQuery(query);
	}

	@Override
	public Object appendSafe(final Object value, final String atAddress) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.appendSafe(getQuery(javaValue), atAddress);
		} else if (javaValue instanceof JsLink) {
			query = node.appendSafe(getLink(javaValue), atAddress);
		} else if (javaValue instanceof JsNode) {
			query = node.appendSafe(getNode(javaValue), atAddress);
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.appendSafe(javaValue, atAddress);
		}

		return createQuery(query);
	}

	@Override
	public Object appendValueSafe(final Object value) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query = node.appendValueSafe(javaValue);

		return createQuery(query);
	}

	@Override
	public Object insert(final Object value, final int atIndex) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.insert(getQuery(javaValue), atIndex);
		} else if (javaValue instanceof JsLink) {
			query = node.insert(getLink(javaValue), atIndex);
		} else if (javaValue instanceof JsNode) {
			query = node.insert(getNode(javaValue), atIndex);
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.insert(javaValue, atIndex);
		}

		return createQuery(query);
	}

	@Override
	public Object insert(final Object value, final String atAddress,
			final int atIndex) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.insert(getQuery(javaValue), atAddress, atIndex);
		} else if (javaValue instanceof JsLink) {
			query = node.insert(getLink(javaValue), atAddress, atIndex);
		} else if (javaValue instanceof JsNode) {
			query = node.insert(getNode(javaValue), atAddress, atIndex);
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.insert(javaValue, atAddress, atIndex);
		}

		return createQuery(query);
	}

	@Override
	public Object insertValue(final Object value, final int atIndex) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query = node.insertValue(javaValue, atIndex);

		return createQuery(query);
	}

	@Override
	public Object insertSafe(final Object value, final int atIndex) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.insertSafe(getQuery(javaValue), atIndex);
		} else if (javaValue instanceof JsLink) {
			query = node.insertSafe(getLink(javaValue), atIndex);
		} else if (javaValue instanceof JsNode) {
			query = node.insertSafe(getNode(javaValue), atIndex);
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.insertSafe(javaValue, atIndex);
		}

		return createQuery(query);
	}

	@Override
	public Object insertSafe(final Object value, final String atAddress,
			final int atIndex) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query;

		if (javaValue instanceof JsQuery) {
			query = node.insertSafe(getQuery(javaValue), atAddress, atIndex);
		} else if (javaValue instanceof JsLink) {
			query = node.insertSafe(getLink(javaValue), atAddress, atIndex);
		} else if (javaValue instanceof JsNode) {
			query = node.insertSafe(getNode(javaValue), atAddress, atIndex);
		} else {
			// assert, should be a value object such as "text" or 33
			query = node.insertSafe(javaValue, atAddress, atIndex);
		}

		return createQuery(query);
	}

	@Override
	public Object insertValueSafe(final Object value, final int atIndex) {
		final Object javaValue = JsOpCommon.getJavaValue(node, value);

		final Query query = node.insertValueSafe(javaValue, atIndex);

		return createQuery(query);
	}

}
