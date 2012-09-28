package io.nextweb.js.operations.impl;

import io.nextweb.Entity;
import io.nextweb.Node;
import io.nextweb.js.JsLink;
import io.nextweb.js.JsNode;
import io.nextweb.js.JsQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.operations.JsEntityAppendOperations;

import org.timepedia.exporter.client.ExporterUtil;

public class Js_EntityAppend implements JsEntityAppendOperations {

	private final Entity node;

	@Override
	public Object append(Object value) {
		Object javaValue = JsOpCommon.getJavaValue(node, value);

		if (javaValue instanceof JsQuery) {
			return ExporterUtil.wrap(JH.jsFactory(node).createQuery(
					node.append(((JsQuery) javaValue).getOriginal())));
		}

		if (javaValue instanceof JsLink) {
			return ExporterUtil.wrap(JH.jsFactory(node).createQuery(
					node.append(((JsLink) javaValue).getOriginal())));
		}

		if (javaValue instanceof JsNode) {
			return ExporterUtil.wrap(JH.jsFactory(node).createQuery(
					node.append(((JsNode) javaValue).getOriginal())));
		}

		// assert, should be a value object such as "text" or 33

		return ExporterUtil.wrap(JH.jsFactory(node).createQuery(
				node.append(javaValue)));
	}

	public Js_EntityAppend(Entity node) {
		super();
		this.node = node;
	}

	@Override
	public Object append(Object value, String atAddress) {
		Object javaValue = JsOpCommon.getJavaValue(node, value);

		if (javaValue instanceof JsQuery) {
			return ExporterUtil.wrap(JH.jsFactory(node)
					.createQuery(
							node.append(((JsQuery) javaValue).getOriginal(),
									atAddress)));
		}

		if (javaValue instanceof JsLink) {
			return ExporterUtil
					.wrap(JH.jsFactory(node).createQuery(
							node.append(((JsLink) javaValue).getOriginal(),
									atAddress)));
		}

		if (javaValue instanceof Node) {
			return ExporterUtil
					.wrap(JH.jsFactory(node).createQuery(
							node.append(((JsNode) javaValue).getOriginal(),
									atAddress)));
		}

		// assert, should be a value object such as "text" or 33

		return ExporterUtil.wrap(JH.jsFactory(node).createQuery(
				node.append(javaValue, atAddress)));
	}

	@Override
	public Object appendValue(Object value) {
		Object javaValue = JsOpCommon.getJavaValue(node, value);

		return ExporterUtil.wrap(JH.jsFactory(node).createQuery(
				node.appendValue(javaValue)));
	}

}
