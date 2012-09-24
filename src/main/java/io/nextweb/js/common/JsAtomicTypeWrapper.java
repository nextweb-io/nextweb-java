package io.nextweb.js.common;

import com.google.gwt.core.client.JavaScriptObject;

public class JsAtomicTypeWrapper extends JavaScriptObject {

	protected JsAtomicTypeWrapper() {
	}

	public final native boolean isWrapper()/*-{
		if (this.type && this.type == "JsAtomicTypeWrapper")
			return true;
		return false;
	}-*/;

	public final native boolean isBoolean()/*-{
		return typeof this.value == "boolean";
	}-*/;

	public final native boolean isDouble()/*-{
		return !isNaN(parseFloat(this.value));
	}-*/;

	public final native boolean isInteger()/*-{
		return this.value % 1 === 0;
	}-*/;

	public final Object getValue() {
		if (isBoolean()) {
			return getBooleanValue();
		}

		if (isInteger()) {
			return getIntValue();
		}

		if (isDouble()) {
			return getDoubleValue();
		}

		return getGenericValue();

	}

	public final native Object getGenericValue()/*-{
		return this.value;
	}-*/;

	public final native int getIntValue()/*-{
		return this.value;
	}-*/;

	public final native double getDoubleValue()/*-{
		return this.value;
	}-*/;

	public final native boolean getBooleanValue()/*-{
		return this.value;
	}-*/;

}
