package io.nextweb.js.utils;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.js.JsNode;
import io.nextweb.js.JsQuery;
import io.nextweb.js.JsSession;
import io.nextweb.js.engine.JsFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.timepedia.exporter.client.ExporterUtil;

public class WrapperCollection {

	private final List<Wrapper> registeredWrappers;
	private final JsFactory factory;

	public void addWrapper(Wrapper wrapper) {
		registeredWrappers.add(wrapper);
	}

	public Object createJsEngineWrapper(Object engineNode) {

		if (engineNode instanceof Query) {
			return JsQuery.wrap((Query) engineNode);
		}

		if (engineNode instanceof Link) {
			return factory.createLink((Link) engineNode);
		}

		if (engineNode instanceof Node) {
			return JsNode.wrap((Node) engineNode);
		}

		if (engineNode instanceof Session) {
			return JsSession.wrap((Session) engineNode);
		}

		return engineNode;
	}

	public Object wrapForJs(Object gwtNode) {

		if (gwtNode instanceof String) {
			return gwtNode;
		}

		if (gwtNode instanceof Integer) {
			return gwtNode;
		}

		if (gwtNode instanceof Short) {
			return gwtNode;
		}

		if (gwtNode instanceof Long) {
			return gwtNode;
		}

		if (gwtNode instanceof Byte) {
			return gwtNode;
		}

		if (gwtNode instanceof Character) {
			return gwtNode;
		}

		if (gwtNode instanceof Double) {
			return gwtNode;
		}

		if (gwtNode instanceof Float) {
			return gwtNode;
		}

		if (gwtNode instanceof Date) {
			return gwtNode;
		}

		for (Wrapper wrapper : registeredWrappers) {
			if (wrapper.accepts(gwtNode)) {
				return wrapper.wrap(gwtNode);
			}
		}

		return ExporterUtil.wrap(gwtNode);
	}

	public WrapperCollection(JsFactory factory) {
		super();
		this.registeredWrappers = new LinkedList<Wrapper>();
		this.factory = factory;
	}

}
