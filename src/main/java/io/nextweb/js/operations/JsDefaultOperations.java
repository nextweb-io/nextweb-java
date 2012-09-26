package io.nextweb.js.operations;

import io.nextweb.Entity;
import io.nextweb.js.operations.impl.Js_EntityAppend;
import io.nextweb.js.operations.impl.Js_EntityRemove;
import io.nextweb.js.operations.impl.Js_Entity_SetValue;

public class JsDefaultOperations {

	Entity entity;

	public JsEntityAppendOperations append() {
		return new Js_EntityAppend(entity);
	}

	public JsEntityRemoveOperations remove() {
		return new Js_EntityRemove(entity);
	}

	public JsEntitySetValueOperations setValue() {
		return new Js_Entity_SetValue(entity);
	}

	public JsDefaultOperations(Entity entity) {
		super();
		this.entity = entity;
	}

}