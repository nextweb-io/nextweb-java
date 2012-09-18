package io.nextweb.operations;

import io.nextweb.Query;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;

public interface SessionOperations {

	public Result<SuccessFail> close();

	public Query load(String uri);

}
