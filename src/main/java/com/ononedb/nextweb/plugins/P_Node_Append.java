package com.ononedb.nextweb.plugins;

import io.nextweb.Node;
import io.nextweb.plugins.core.Plugin_Node_Append;
import one.core.dsl.CoreDsl;
import one.core.nodes.OneTypedReference;
import one.core.nodes.OneValue;
import one.utils.OneUtilsStrings;

import com.ononedb.nextweb.OnedbNode;
import com.ononedb.nextweb.common.H;

public class P_Node_Append implements Plugin_Node_Append<OnedbNode> {

	OnedbNode entity;

	@Override
	public Node append(Object value) {
		CoreDsl dsl = H.dsl(entity);

		String address = value.toString();

		if (address.length() > 8) {
			address = address.substring(0, 7);
		}

		String cleanedString = "";
		for (int i = 0; i <= address.length() - 1; i++) {
			if (!OneUtilsStrings.isSimpleCharacter(address.charAt(i))) {
				cleanedString = cleanedString + "_";
			} else {
				cleanedString = cleanedString + address.charAt(i);
			}
		}

		cleanedString = cleanedString
				+ (dsl.selectFrom(dsl.reference(entity.getUri()))
						.allChildrenFast().in(H.client(entity)).size() + 1);

		OneValue<Object> appendedValue = dsl.append(value)
				.to(dsl.reference(entity.getUri()))
				.atAddress("./" + cleanedString).in(H.client(entity));

		return H.factory(entity).createNode(H.session(entity),
				entity.getExceptionManager(),
				dsl.reference(appendedValue.getId()));

	}

	@Override
	public Node append(Object value, String atAddress) {
		CoreDsl dsl = H.dsl(entity);

		String address = atAddress;

		OneValue<Object> appendedValue = dsl.append(value)
				.to(dsl.reference(entity.getUri())).atAddress(address)
				.in(H.client(entity));

		return H.factory(entity).createNode(H.session(entity),
				entity.getExceptionManager(),
				dsl.reference(appendedValue.getId()));
	}

	@Override
	public Node appendValue(Object value) {
		CoreDsl dsl = H.dsl(entity);

		Object appended = dsl.append(value).to(dsl.reference(entity.getUri()))
				.in(H.client(entity));

		OneTypedReference<Object> appendedRef = dsl.reference(appended).in(
				H.client(entity));

		return H.factory(entity).createNode(H.session(entity),
				entity.getExceptionManager(), appendedRef);
	}

	@Override
	public void injectObject(OnedbNode obj) {
		this.entity = obj;
	}

}
