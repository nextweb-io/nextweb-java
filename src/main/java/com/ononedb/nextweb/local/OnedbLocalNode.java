package com.ononedb.nextweb.local;

import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

public interface OnedbLocalNode {

	public Result<Success> shutdown();

}
