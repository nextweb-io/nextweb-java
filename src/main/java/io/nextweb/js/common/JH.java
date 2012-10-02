package io.nextweb.js.common;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;
import io.nextweb.js.JsEntity;
import io.nextweb.js.JsEntityList;
import io.nextweb.js.JsLinkList;
import io.nextweb.js.JsLinkListQuery;
import io.nextweb.js.JsNode;
import io.nextweb.js.engine.JsFactory;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.operations.JsDefaultOperations;
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

	public static final void getNode(final Entity entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(CallbackFactory.lazyCallback(
				entityResult.getSession(), entityResult.getExceptionManager(),
				new Closure<Node>() {

					@Override
					public void apply(final Node o) {
						callback.apply(ExporterUtil.wrap(jsFactory(o)
								.createNode(o)));
					}
				}));

	}

	public static final <E extends Entity> Object get(
			final JsEntity<E> forEntity, final Object... params) {

		final E node = forEntity.getOriginal();

		if (params.length == 0) {

			return ExporterUtil.wrap(JH.getNode(node));
		}

		if (params.length > 1) {
			throw new IllegalArgumentException(
					"Only one argument of type JsClosure is supported.");
		}

		JH.getNode(node, JH.asJsClosure((JavaScriptObject) params[0], JH
				.jsFactory(node).getWrappers()));

		return ExporterUtil.wrap(forEntity);

	}

	public static final <E extends EntityList> Object get(
			final JsEntityList<E> forEntity, final Object... params) {

		final E list = forEntity.getOriginal();

		if (params.length == 0) {

			return ExporterUtil.wrap(JH.getNodeList(list));
		}

		if (params.length > 1) {
			throw new IllegalArgumentException(
					"Only one argument of type JsClosure is supported.");
		}

		JH.getNodeList(list, JH.asJsClosure((JavaScriptObject) params[0], JH
				.jsFactory(list).getWrappers()));

		return ExporterUtil.wrap(forEntity);

	}

	public static final Object get(final JsLinkListQuery forEntity,
			final Object... params) {

		final LinkListQuery list = forEntity.getOriginal();

		if (params.length == 0) {

			return ExporterUtil.wrap(JH.getLinkList(list));
		}

		if (params.length > 1) {
			throw new IllegalArgumentException(
					"Only one argument of type JsClosure is supported.");
		}

		JH.getLinkList(list, JH.asJsClosure((JavaScriptObject) params[0], JH
				.jsFactory(list).getWrappers()));

		return ExporterUtil.wrap(forEntity);

	}

	public static final Object getNodeList(final Result<NodeList> entityResult) {
		assert entityResult != null;

		final NodeList result = entityResult.get();
		if (result == null) {
			return null;
		}
		return ExporterUtil.wrap(jsFactory(result).createNodeList(result));
	}

	public static final JsNode getNode(final Result<Node> entityResult) {
		assert entityResult != null;

		final Node result = entityResult.get();
		if (result == null) {
			return null;
		}

		return jsFactory(result).createNode(result);
	}

	public static final void getNodeList(final EntityList entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(CallbackFactory.lazyCallback(entityResult,
				new Closure<NodeList>() {

					@Override
					public void apply(final NodeList o) {
						callback.apply(ExporterUtil.wrap(jsFactory(o)
								.createNodeList(o)));
					}

				}));

	}

	public static final boolean isJsString(final Object value) {
		return value instanceof String;
	}

	public static final boolean isJsInteger(final Object value) {
		return value instanceof Integer || value instanceof Short
				|| value instanceof Long || value instanceof Byte;
	}

	public static final boolean isJsDouble(final Object value) {
		return value instanceof Float || value instanceof Double;
	}

	public static final boolean isJsBoolean(final Object value) {
		return value instanceof Boolean;
	}

	public static final boolean isBasicJsType(final Object node) {
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
			final Object node) {

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
	public static final Object wrapNonBasicNode(final Object node) {

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

	public static final void getLinkList(final LinkListQuery entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(CallbackFactory.lazyCallback(entityResult,
				new Closure<LinkList>() {

					@Override
					public void apply(final LinkList o) {
						callback.apply(ExporterUtil.wrap(jsFactory(o)
								.createLinkList(o)));
					}

				}));

	}

	public static final JsLinkList getLinkList(
			final Result<LinkList> entityResult) {
		assert entityResult != null;

		final LinkList result = entityResult.get();
		if (result == null) {
			return null;
		}
		return jsFactory(result).createLinkList(result);
	}

	public static final void triggerCallback(final JavaScriptObject fn,
			final WrapperCollection wrappers, final Object[] params) {
		triggerCallbackJs(fn,
				ExporterUtil.wrap(JsArray.wrap(toJsoArray(params, wrappers))));
	}

	public static final JavaScriptObject[] toJsoArray(final Object[] array,
			final WrapperCollection wrappers) {
		final JavaScriptObject[] result = new JavaScriptObject[array.length];
		for (int i = 0; i <= array.length - 1; i++) {
			final Object rawWrapped = wrappers.wrapValueObjectForJs(wrappers
					.createJsEngineWrapper(array[i]));
			if (rawWrapped instanceof JavaScriptObject) {
				result[i] = (JavaScriptObject) rawWrapped;
			} else {
				result[i] = ExporterUtil.wrap(rawWrapped);
			}
		}
		return result;
	}

	public static final JsClosure asJsClosure(final JavaScriptObject fn,
			final WrapperCollection wrappers) {
		return new JsClosure() {

			@Override
			public void apply(final Object result) {
				triggerCallbackSimpleJs(fn, (JavaScriptObject) result);
			}
		};

	}

	public static final native void triggerCallbackSimpleJs(
			JavaScriptObject fn, JavaScriptObject parameter)/*-{
															fn(parameter);
															}-*/;

	public static final native void triggerCallbackJs(JavaScriptObject fn,
			JavaScriptObject jsArray)/*-{
										fn.apply(this, jsArray.getArray());
										}-*/;

	public static JsFactory jsFactory(final LinkListQuery listQuery) {
		final Session session = listQuery.getSession();
		return ((NextwebEngineJs) session.getEngine()).jsFactory();

	}

	public static final JsFactory jsFactory(final Entity entity) {
		return jsFactory(entity.getSession());
	}

	public static final JsDefaultOperations op(final Entity entity) {
		return jsFactory(entity).op(entity);
	}

	public static final JsFactory jsFactory(final Session session) {
		return ((NextwebEngineJs) session.getEngine()).jsFactory();
	}

	public static final JsFactory jsFactory(final EntityList entityList) {
		return jsFactory(entityList.getSession());
	}

}
