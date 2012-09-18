package com.ononedb.nextweb.common;

import one.core.dsl.CoreDsl;

public class H {

	public static CoreDsl dsl(OnedbObject fromObj) {
		return fromObj.getOnedbSession().getClient().one();
	}

}
