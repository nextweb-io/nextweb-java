package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.nodes.Bytes;
import io.nextweb.nodes.Json;
import io.nextweb.nodes.Port;
import io.nextweb.nodes.Token;
import io.nextweb.promise.Deferred;
import io.nextweb.promise.Result;
import io.nextweb.promise.exceptions.ExceptionManager;

public interface Factory {

    public <ResultType> Result<ResultType> createResult(
            ExceptionManager exceptionManager, Session session,
            Deferred<ResultType> asyncResult);

    public Bytes createBytes(Session session, byte[] data, String mimetype);

    public Port createPort(Session session, String uri, String secret);

    /**
     * Valid authorizations: "write", "read", "readwrite"
     * 
     * @param session
     * @param secret
     * @param grantedAuthorization
     * @param identification
     * @return
     */
    public Token createToken(Session session, String secret,
            String grantedAuthorization, String identification);

    public Json createJson(Session session, String json);

}
