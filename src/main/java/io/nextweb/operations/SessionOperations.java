package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;

public interface SessionOperations {

	public Result<SuccessFail> close();

	public Link node(String uri);

	public Result<SuccessFail> getAll(Result<?>... results);

}
