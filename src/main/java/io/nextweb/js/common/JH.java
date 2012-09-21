package io.nextweb.js.common;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.LinkList;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.fn.Result;
import io.nextweb.fn.ResultCallback;
import io.nextweb.js.engine.JsFactory;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.utils.WrapperCollection;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * JavaScript helper methods.
 * 
 * @author mroh004
 * 
 */
public class JH {

	public static final void get(Result<Node> entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(new ResultCallback<Node>() {

			@Override
			public void onSuccess(Node result) {
				callback.apply(ExporterUtil.wrap(jsFactory(result).createNode(
						result)));
			}
		});
	}

	public static final Object get(Result<Node> entityResult) {
		assert entityResult != null;

		final Node result = entityResult.get();
		if (result == null) {
			return null;
		}
		return ExporterUtil.wrap(jsFactory(result).createNode(result));
	}

	public static final void getNodeList(Result<NodeList> entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(new ResultCallback<NodeList>() {

			@Override
			public void onSuccess(NodeList result) {
				callback.apply(ExporterUtil.wrap(jsFactory(result)
						.createNodeList(result)));
			}
		});
	}

	public static final Object getNodeList(Result<NodeList> entityResult) {
		assert entityResult != null;

		final NodeList result = entityResult.get();
		if (result == null) {
			return null;
		}
		return ExporterUtil.wrap(jsFactory(result).createNodeList(result));
	}

	public static final void getLinkList(Result<LinkList> entityResult,
			final JsClosure callback) {
		assert entityResult != null;
		assert callback != null;

		entityResult.get(new ResultCallback<LinkList>() {

			@Override
			public void onSuccess(LinkList result) {
				callback.apply(ExporterUtil.wrap(jsFactory(result)
						.createLinkList(result)));
			}
		});
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
		return ((NextwebEngineJs) entity.getSession().getEngine()).jsFactory();
	}

	public static final JsFactory jsFactory(EntityList entityList) {
		return ((NextwebEngineJs) entityList.getSession().getEngine())
				.jsFactory();
	}

}
