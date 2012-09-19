package com.ononedb.nextweb.js.utils;

import io.nextweb.js.utils.Wrapper;
import one.common.nodes.OneJSON;

import com.google.gwt.core.client.JavaScriptObject;

public class OnedbWrapper {

	public final static Wrapper ONEJSON = new Wrapper() {

		@Override
		public Object wrap(Object input) {
			return getJsObject(((OneJSON) input).getJSONString());
		}

		@Override
		public boolean accepts(Object input) {
			return input instanceof OneJSON;
		}
	};

	public final native static JavaScriptObject getJsObject(final String json)/*-{
		return eval("(" + json + ")");
	}-*/;

}
