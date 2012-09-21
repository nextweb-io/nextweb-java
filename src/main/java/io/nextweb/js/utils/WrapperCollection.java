package io.nextweb.js.utils;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.Session;
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
			return factory.createQuery((Query) engineNode);
		}

		if (engineNode instanceof Link) {
			return factory.createLink((Link) engineNode);
		}

		if (engineNode instanceof Node) {
			return factory.createNode((Node) engineNode);
		}

		if (engineNode instanceof Session) {
			return factory.createSession((Session) engineNode);
		}

		if (engineNode instanceof NodeListQuery) {
			return factory.createNodeListQuery((NodeListQuery) engineNode);
		}

		if (engineNode instanceof NodeList) {
			return factory.createNodeList((NodeList) engineNode);
		}

		if (engineNode instanceof LinkList) {
			return factory.createLinkList((LinkList) engineNode);
		}

		if (engineNode instanceof LinkListQuery) {
			return factory.createLinkListQuery((LinkListQuery) engineNode);
		}

		return engineNode;
	}

	public Object wrapValueObjectForJs(Object gwtNode) {

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
