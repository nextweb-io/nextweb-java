package com.ononedb.nextweb.local;

import io.nextweb.fn.Result;
import io.nextweb.fn.Success;

public interface OnedbLocalDb {

	public Result<Success> shutdown();

}
