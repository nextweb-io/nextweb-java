package com.ononedb.nextweb;

import com.ononedb.nextweb.internal.OnedbFactory;

import io.nextweb.engine.NextwebEngine;


public interface OnedbNextwebEngine extends NextwebEngine {

	public OnedbFactory getFactory();

}
