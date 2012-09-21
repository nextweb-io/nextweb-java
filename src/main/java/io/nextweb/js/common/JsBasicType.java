package io.nextweb.js.common;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsBasicType implements Exportable {

	private Object value;

	@Export
	public int intValue() {
		return (Integer) value;
	}

	@Export
	public String stringValue() {
		return (String) value;
	}

	@Export
	public JavaScriptObject booleanValue() {
		if (((Boolean) value).booleanValue() == true) {
			return booleanTrue();
		} else {
			return booleanFalse();
		}
	}

	private final native JavaScriptObject booleanTrue()/*-{
		var val = {
			value : true
		};
		return val;
	}-*/;

	private final native JavaScriptObject booleanFalse()/*-{
		var val = {
			value : false
		};
		return val;
	}-*/;

	@Export
	public double doubleValue() {

		return (Double) value;
	}

	@Export
	public int isString() {
		return JH.isJsString(value) ? 1 : 0;
	}

	@Export
	public int isInt() {
		// GWT.log("is Integer: " + value + " " + JH.isJsInteger(value));
		return JH.isJsInteger(value) ? 1 : 0;
	}

	@Export
	public int isDouble() {
		return JH.isJsDouble(value) ? 1 : 0;
	}

	@Export
	public int isBoolean() {
		return value instanceof Boolean ? 1 : 0;
	}

	@Export
	public int isJsBasicType() {
		return 1;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public static JsBasicType wrap(Object node) {
		JsBasicType jsBasicType = new JsBasicType();

		jsBasicType.setValue(node);
		assert jsBasicType.isString() != 0 || jsBasicType.isInt() != 0
				|| jsBasicType.isDouble() != 0 || jsBasicType.isBoolean() != 0 : "Attempting to wrap non-basic type.";

		return jsBasicType;
	}

	public JsBasicType() {
		super();
	}

}
