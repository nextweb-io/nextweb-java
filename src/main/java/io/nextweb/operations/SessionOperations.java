package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.common.LoginResult;
import io.nextweb.common.Postbox;
import io.nextweb.fn.BasicResult;
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

	public Session getAll(BasicResult<?>... results);

	public Result<SuccessFail> getAll(boolean asynchronous,
			BasicResult<?>... results);

	public Query seed();

	public Query seed(String seedType);

	public Query createRealm(String realmTitle, String realmType, String apiKey);

	public Result<Postbox> createPostbox(String realmTitle, String postboxType,
			String apiKey);

	public Result<Success> post(Object value, String toUri, String secret);

	public LoginResult login(String userName, String password);

}
