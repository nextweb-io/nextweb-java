package io.nextweb.common;

import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

public interface LocalServer {

	public Result<Success> shutdown();

}
