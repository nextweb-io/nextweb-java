package com.ononedb.nextweb.local;

import io.nextweb.Session;
import io.nextweb.common.LocalServer;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

import com.ononedb.nextweb.OnedbNextwebEngine;

public interface OnedbLocalServer extends LocalServer {

	public Session createSession();

	public OnedbNextwebEngine getEngine();

	@Override
	public Result<Success> shutdown();

}
