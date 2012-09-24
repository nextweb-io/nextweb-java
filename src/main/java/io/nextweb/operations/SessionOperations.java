package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.Session;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.fn.SuccessFail;

public interface SessionOperations {

	/**
	 * Will be executed eagerly.
	 * 
	 * @return
	 */
	public Result<Success> close();

	/**
	 * Will be executed eagerly.
	 * 
	 * @return
	 */
	public Result<Success> commit();

	public Link node(String uri);

	public Link node(String uri, String secret);

	public Session getAll(Result<?>... results);

	public Result<SuccessFail> getAll(boolean asynchronous,
			Result<?>... results);

}
