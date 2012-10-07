package io.nextweb.js.operations.entity.impl;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.Session;
import io.nextweb.js.common.JH;

public class JsOpCommon {

	public static Object getJavaValue(final Entity entity, final Object value) {
		final Object javaValue = JH.jsFactory(entity).getWrappers()
				.wrapValueObjectForJava(value);
		return javaValue;
	}

	public static Object getJavaValue(final Session entity, final Object value) {
		final Object javaValue = JH.jsFactory(entity).getWrappers()
				.wrapValueObjectForJava(value);
		return javaValue;
	}

	public static Object getJavaValue(final EntityList list,
			final Object newValue) {
		return getJavaValue(list.getSession(), newValue);
	}

}
