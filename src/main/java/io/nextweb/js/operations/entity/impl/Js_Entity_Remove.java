package io.nextweb.js.operations.entity.impl;

import io.nextweb.Entity;
import io.nextweb.js.JsLink;
import io.nextweb.js.JsNode;
import io.nextweb.js.JsQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsResult;
import io.nextweb.js.operations.entity.JsEntityRemoveOperations;

import org.timepedia.exporter.client.ExporterUtil;

public class Js_Entity_Remove implements JsEntityRemoveOperations {

	private final Entity node;

	@Override
	public JsResult remove(Object entity) {
		Object javaEntity = ExporterUtil.gwtInstance(entity);

		if (javaEntity instanceof JsNode) {

			return JH.jsFactory(node).createResult(
					node.remove(((JsNode) javaEntity).getOriginal()));
		}

		if (javaEntity instanceof JsLink) {
			return JH.jsFactory(node).createResult(
					node.remove(((JsLink) javaEntity).getOriginal()));
		}

		if (javaEntity instanceof JsQuery) {
			return JH.jsFactory(node).createResult(
					node.remove(((JsQuery) javaEntity).getOriginal()));
		}

		throw new IllegalArgumentException(
				"Only JsLink, JsNode and JsQuery objects can be passed as parameters for the remove operation and not ["
						+ entity + ":" + entity.getClass() + "]");
	}

	public Js_Entity_Remove(Entity node) {
		super();
		this.node = node;
	}

}
