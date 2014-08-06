package io.nextweb.common;

import de.mxro.fn.Success;
import io.nextweb.fn.Result;

public interface LocalServer {

	public Result<Success> shutdown();

}
