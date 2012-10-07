package com.ononedb.nextweb.plugins.impl;

import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.NodeList;
import io.nextweb.Query;
import io.nextweb.fn.Calculation;
import io.nextweb.fn.Fn;

import com.ononedb.nextweb.OnedbEntityList;
import com.ononedb.nextweb.plugins.Plugin_EntityList_SetValue;

public class P_EntityList_SetValue implements
		Plugin_EntityList_SetValue<OnedbEntityList> {

	OnedbEntityList entity;

	@Override
	public void injectObject(final OnedbEntityList obj) {
		this.entity = obj;
	}

	@Override
	public ListQuery setValue(final Object newValue) {

		final Calculation<Node, Query> operation = new Calculation<Node, Query>() {

			@Override
			public Query apply(final Node input) {
				return input.setValue(newValue);
			}
		};
		final ListQuery joinNodeResults = ListCommon.joinNodeResults(entity,
				operation);

		joinNodeResults.get(Fn.doNothing(NodeList.class));

		return joinNodeResults;
	}

	@Override
	public ListQuery setValueSafe(final Object newValue) {
		final Calculation<Node, Query> operation = new Calculation<Node, Query>() {

			@Override
			public Query apply(final Node input) {
				return input.setValueSafe(newValue);
			}
		};
		final ListQuery joinNodeResults = ListCommon.joinNodeResults(entity,
				operation);

		joinNodeResults.get(Fn.doNothing(NodeList.class));

		return joinNodeResults;
	}

}
