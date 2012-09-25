package io.nextweb.js.operations.impl;

import io.nextweb.Entity;
import io.nextweb.js.common.JH;

public class JsOpCommon {

	public static Object getJavaValue(Entity entity, Object value) {
		Object javaValue = JH.jsFactory(entity).getWrappers()
				.wrapValueObjectForJava(value);
		return javaValue;
	}

}
