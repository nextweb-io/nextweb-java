package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;

public interface SessionOperations {

	/**
	 * Will be executed eagerly.
	 * 
	 * @return
	 */
	public Result<SuccessFail> close();

	public Link node(String uri);

	public void getAll(Result<?>... results);

	public Result<SuccessFail> getAll(boolean asynchronous,
			Result<?>... results);

}
