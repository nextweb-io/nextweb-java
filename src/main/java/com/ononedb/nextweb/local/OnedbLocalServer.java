package com.ononedb.nextweb.local;

import io.nextweb.Session;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

import com.ononedb.nextweb.OnedbNextwebEngine;

public interface OnedbLocalServer {

	public Session createSession();

	public OnedbNextwebEngine getEngine();

	public Result<Success> shutdown();

}
