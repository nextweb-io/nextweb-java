package io.nextweb.js.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;

import com.google.gwt.core.client.JavaScriptObject;

@Export
public class JsArray implements Exportable {

	List<JavaScriptObject> arrayList;

	@Export
	public void append(JavaScriptObject obj) {
		this.arrayList.add(obj);
	}

	@Export
	public void setArray(final JavaScriptObject[] ar) {
		this.arrayList = Arrays.asList(ar);
	}

	@Export
	public JavaScriptObject[] getArray() {
		return this.arrayList.toArray(new JavaScriptObject[0]);
	}

	public static JsArray wrap(JavaScriptObject[] array) {
		JsArray jsArray = new JsArray();
		jsArray.setArray(array);
		return jsArray;
	}

	public JsArray() {
		super();
		this.arrayList = new ArrayList<JavaScriptObject>(0);
	}

}
