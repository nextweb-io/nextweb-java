package com.ononedb.nextweb;

import io.nextweb.EntityList;

public interface OnedbEntityList<ResultType extends EntityList<?>> extends
		OnedbObject, EntityList<ResultType> {

}
