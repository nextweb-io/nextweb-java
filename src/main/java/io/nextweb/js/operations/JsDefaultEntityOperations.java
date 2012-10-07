package io.nextweb.js.operations;

import io.nextweb.Entity;
import io.nextweb.js.operations.entity.JsEntityAppendOperations;
import io.nextweb.js.operations.entity.JsEntityRemoveOperations;
import io.nextweb.js.operations.entity.JsEntitySetValueOperations;
import io.nextweb.js.operations.entity.impl.Js_Entity_Append;
import io.nextweb.js.operations.entity.impl.Js_Entity_Remove;
import io.nextweb.js.operations.entity.impl.Js_Entity_SetValue;

public class JsDefaultEntityOperations {

	Entity entity;

	public JsEntityAppendOperations append() {
		return new Js_Entity_Append(entity);
	}

	public JsEntityRemoveOperations remove() {
		return new Js_Entity_Remove(entity);
	}

	public JsEntitySetValueOperations setValue() {
		return new Js_Entity_SetValue(entity);
	}

	public JsDefaultEntityOperations(Entity entity) {
		super();
		this.entity = entity;
	}

}
