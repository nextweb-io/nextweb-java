package com.ononedb.nextweb;

import io.nextweb.Session;
import io.nextweb.engine.NextwebEngine;

import com.ononedb.nextweb.internal.OnedbFactory;

public interface OnedbNextwebEngine extends NextwebEngine {

	public OnedbFactory getFactory();

	/**
	 * Assure to execute within the sessions worker thread.
	 * 
	 * @param forSession
	 * @param task
	 */
	public void runSafe(Session forSession, Runnable task);

}
