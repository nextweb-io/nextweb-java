package io.nextweb.js.utils;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.js.common.JsAtomicTypeWrapper;
import io.nextweb.js.engine.JsFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import one.common.nodes.v01.OneJSONData;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

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

	public Object wrapValueObjectForJava(Object jsNode) {

		if (jsNode instanceof String) {
			return jsNode;
		}

		if (jsNode instanceof Integer) {
			return jsNode;
		}

		if (jsNode instanceof Boolean) {
			return jsNode;
		}

		if (jsNode instanceof Short) {
			return jsNode;
		}

		if (jsNode instanceof Long) {
			return jsNode;
		}

		if (jsNode instanceof Byte) {
			return jsNode;
		}

		if (jsNode instanceof Character) {
			return jsNode;
		}

		if (jsNode instanceof Double) {
			return jsNode;
		}

		if (jsNode instanceof Float) {
			return jsNode;
		}

		if (jsNode instanceof Date) {
			return jsNode;
		}

		final Object obj = ExporterUtil.gwtInstance(jsNode);

		if (obj instanceof JavaScriptObject) {
			final JavaScriptObject jsobj = (JavaScriptObject) obj;
			if (isDate(jsobj)) {

				return dateFromJsDate(jsobj);
			}

			final JsAtomicTypeWrapper wrapper = (jsobj).cast();

			if (wrapper.isWrapper()) {
				return wrapper.getValue();
			}

		}

		if (obj instanceof JavaScriptObject || obj instanceof JSONValue) {
			final String jsonData = new JSONObject((JavaScriptObject) obj)
					.toString();

			return new OneJSONData(jsonData);
		}

		return obj;

	}

	public final native static JavaScriptObject getJsObj(Object o)/*-{
		return o;
	}-*/;

	public final native static boolean isDate(final JavaScriptObject d)/*-{
		return (d && d.getTime && typeof d.getTime == 'function') ? true
				: false;
	}-*/;

	public final static Date dateFromJsDate(final JavaScriptObject d) {
		final String dateStr = timeFromJsDate(d);

		final long time = Long.valueOf(dateStr.substring(1));

		return new Date(time);
	}

	public final native static String timeFromJsDate(final JavaScriptObject d)/*-{
		return "t" + d.getTime();
	}-*/;

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

		if (gwtNode instanceof Boolean) {
			return gwtNode;
		}

		for (Wrapper wrapper : registeredWrappers) {
			if (wrapper.accepts(gwtNode)) {
				Object wrapped = wrapper.wrap(gwtNode);
				if (!(wrapped instanceof JavaScriptObject)) {
					wrapped = ExporterUtil.wrap(wrapped);
				}
				return wrapped;
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
