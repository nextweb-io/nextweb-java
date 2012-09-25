package io.nextweb.js.common;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.NodeListQuery;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.js.engine.JsFactory;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.utils.WrapperCollection;
import io.nextweb.operations.callbacks.CallbackFactory;

import java.util.Date;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * JavaScript helper methods.
 * 
 * @author mroh004
 * 
 */
public class JH {

	public static final void getNode(Entity entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(CallbackFactory.lazyCallback(
				entityResult.getSession(), entityResult.getExceptionManager(),
				new Closure<Node>() {

					@Override
					public void apply(Node o) {
						callback.apply(ExporterUtil.wrap(jsFactory(o)
								.createNode(o)));
					}
				}));

	}

	public static final JavaScriptObject getNode(Result<Node> entityResult) {
		assert entityResult != null;

		final Node result = entityResult.get();
		if (result == null) {
			return null;
		}

		return ExporterUtil.wrap(jsFactory(result).createNode(result));
	}

	public static final void getNodeList(NodeListQuery entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(CallbackFactory.lazyCallback(entityResult,
				new Closure<NodeList>() {

					@Override
					public void apply(NodeList o) {
						callback.apply(ExporterUtil.wrap(jsFactory(o)
								.createNodeList(o)));
					}

				}));

	}

	public static final boolean isJsString(Object value) {
		return value instanceof String;
	}

	public static final boolean isJsInteger(Object value) {
		return value instanceof Integer || value instanceof Short
				|| value instanceof Long || value instanceof Byte;
	}

	public static final boolean isJsDouble(Object value) {
		return value instanceof Float || value instanceof Double;
	}

	public static final boolean isJsBoolean(Object value) {
		return value instanceof Boolean;
	}

	public static final boolean isBasicJsType(Object node) {
		return isJsString(node) || isJsInteger(node) || isJsDouble(node)
				|| isJsBoolean(node);
	}

	/**
	 * Will wrap all objects including Strings, Integers etc into
	 * JavaScriptObjects. Basic JS types (such as String) will be placed in a
	 * special wrapper Object JsBasicType.
	 * 
	 * @param node
	 * @return
	 */
	public static final JavaScriptObject forceWrapIntoJavaScriptObject(
			Object node) {

		if (isBasicJsType(node)) {
			return ExporterUtil.wrap(JsBasicType.wrap(node));
		}

		return ExporterUtil.wrap(wrapNonBasicNode(node));

	}

	/**
	 * Wraps this object into a JavaScriptObject, if its not an atomic JS type
	 * such as 'string', 'number', ...
	 * 
	 * @param node
	 * @return
	 */
	public static final Object wrapNonBasicNode(Object node) {

		if (isBasicJsType(node)) {
			return node;
		}

		if (node instanceof Character) {
			return node;
		}

		if (node instanceof Date) {
			return node;
		}

		return ExporterUtil.wrap(node);

	}

	public static final Object getNodeList(Result<NodeList> entityResult) {
		assert entityResult != null;

		final NodeList result = entityResult.get();
		if (result == null) {
			return null;
		}
		return ExporterUtil.wrap(jsFactory(result).createNodeList(result));
	}

	public static final void getLinkList(LinkListQuery entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(CallbackFactory.lazyCallback(entityResult,
				new Closure<LinkList>() {

					@Override
					public void apply(LinkList o) {
						callback.apply(ExporterUtil.wrap(jsFactory(o)
								.createLinkList(o)));
					}

				}));

	}

	public static final Object getLinkList(Result<LinkList> entityResult) {
		assert entityResult != null;

		final LinkList result = entityResult.get();
		if (result == null) {
			return null;
		}
		return ExporterUtil.wrap(jsFactory(result).createLinkList(result));
	}

	public static final void triggerCallback(JavaScriptObject fn,
			WrapperCollection wrappers, Object[] params) {
		triggerCallbackJs(fn,
				ExporterUtil.wrap(JsArray.wrap(toJsoArray(params, wrappers))));
	}

	public static final JavaScriptObject[] toJsoArray(Object[] array,
			WrapperCollection wrappers) {
		JavaScriptObject[] result = new JavaScriptObject[array.length];
		for (int i = 0; i <= array.length - 1; i++) {
			Object rawWrapped = wrappers.wrapValueObjectForJs(wrappers
					.createJsEngineWrapper(array[i]));
			if (rawWrapped instanceof JavaScriptObject) {
				result[i] = (JavaScriptObject) rawWrapped;
			} else {
				result[i] = ExporterUtil.wrap(rawWrapped);
			}
		}
		return result;
	}

	public static final native void triggerCallbackJs(JavaScriptObject fn,
			JavaScriptObject jsArray)/*-{
		fn.apply(this, jsArray.getArray());
	}-*/;

	public static final JsFactory jsFactory(Entity entity) {
		return jsFactory(entity.getSession());
	}

	public static final JsFactory jsFactory(Session session) {
		return ((NextwebEngineJs) session.getEngine()).jsFactory();
	}

	public static final JsFactory jsFactory(EntityList<?> entityList) {
		return jsFactory(entityList.getSession());
	}

}
