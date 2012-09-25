package io.nextweb.js.operations.impl;

import io.nextweb.Entity;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.js.JsEntity;
import io.nextweb.js.common.JH;
import io.nextweb.js.fn.JsResult;
import io.nextweb.js.operations.JsEntitySetValueOperations;

public class Js_Entity_SetValue<E extends JsEntity<?>> implements
		JsEntitySetValueOperations<E> {

	private final Entity entity;

	@SuppressWarnings("unchecked")
	@Override
	public E setValue(Object newValue) {
		final Object javaValue = JsOpCommon.getJavaValue(entity, newValue);

		entity.setValue(javaValue);

		return (E) JH.jsFactory(entity.getSession()).getWrappers()
				.createJsEngineWrapper(entity);
	}

	@Override
	public E value(Object newValue) {
		return setValue(newValue);
	}

	@Override
	public JsResult setValueSafe(Object newValue) {
		final Object javaValue = JsOpCommon.getJavaValue(entity, newValue);

		Result<Success> setValueSafeResult = entity.setValueSafe(javaValue);

		return JH.jsFactory(entity.getSession()).createResult(
				setValueSafeResult);
	}

	public Js_Entity_SetValue(Entity entity) {
		super();
		this.entity = entity;
	}

}
