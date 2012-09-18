package com.ononedb.nextweb;

import io.nextweb.engine.NextwebEngine;

import com.ononedb.nextweb.common.OnedbFactory;

public interface OnedbNextwebEngine extends NextwebEngine {

	public OnedbFactory getFactory();

}
