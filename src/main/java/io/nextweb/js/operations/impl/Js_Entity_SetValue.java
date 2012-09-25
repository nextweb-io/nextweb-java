package io.nextweb.js.operations.impl;

import io.nextweb.Entity;
import io.nextweb.Query;
import io.nextweb.js.JsQuery;
import io.nextweb.js.common.JH;
import io.nextweb.js.operations.JsEntitySetValueOperations;

public class Js_Entity_SetValue implements JsEntitySetValueOperations {

	private final Entity entity;

	@Override
	public JsQuery setValue(Object newValue) {
		final Object javaValue = JsOpCommon.getJavaValue(entity, newValue);

		return JH.jsFactory(entity.getSession()).createQuery(
				entity.setValue(javaValue));
	}

	@Override
	public JsQuery value(Object newValue) {
		return setValue(newValue);
	}

	@Override
	public JsQuery setValueSafe(Object newValue) {
		final Object javaValue = JsOpCommon.getJavaValue(entity, newValue);

		Query setValueSafeResult = entity.setValueSafe(javaValue);

		return JH.jsFactory(entity.getSession())
				.createQuery(setValueSafeResult);
	}

	public Js_Entity_SetValue(Entity entity) {
		super();
		this.entity = entity;
	}

}
