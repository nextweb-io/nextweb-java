package io.nextweb.engine;

import io.nextweb.Session;
import io.nextweb.nodes.Bytes;
import io.nextweb.nodes.Json;
import io.nextweb.nodes.Reference;
import io.nextweb.nodes.Token;
import io.nextweb.promise.NextwebOperation;
import io.nextweb.promise.NextwebPromise;
import io.nextweb.promise.exceptions.NextwebExceptionManager;

public interface Factory {

    public <ResultType> NextwebPromise<ResultType> createPromise(NextwebExceptionManager exceptionManager,
            Session session, NextwebOperation<ResultType> asyncResult);

    public Bytes createBytes(Session session, byte[] data, String mimetype);

    /**
     * Create a {@link Reference} to a node.
     * 
     * @param session
     * @param uri
     * @param secret
     * @return
     */
    public Reference createReference(final Session session, final String uri, final String secret);

    @Deprecated
    public Reference createPort(Session session, String uri, String secret);

    /**
     * Valid authorizations: "write", "read", "readwrite"
     * 
     * @param session
     *            A reference to a session. This is required to create this
     *            object.
     * @param secret
     *            The secret for this token.
     * @param grantedAuthorization
     *            The type of authorization granted for this token.
     * @param identification
     *            The identification (uri) that this token refers too. Optional,
     *            can be "".
     * @return The newly created token.
     */
    public Token createToken(Session session, String secret, String grantedAuthorization, String identification);

    public Json createJson(Session session, String json);

}
