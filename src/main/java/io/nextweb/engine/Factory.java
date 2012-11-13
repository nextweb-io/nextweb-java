package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Result;
import io.nextweb.nodes.Bytes;
import io.nextweb.nodes.Port;
import io.nextweb.nodes.Token;
import io.nextweb.operations.exceptions.ExceptionManager;

public interface Factory {

	public <ResultType> Result<ResultType> createResult(
			ExceptionManager exceptionManager, Session session,
			AsyncResult<ResultType> asyncResult);

	public Bytes createBytes(byte[] data, String mimetype);

	public Port createPort(String uri, String secret);

	public Token createToken(String secret, String grantedAuthorization,
			String identification);

}
