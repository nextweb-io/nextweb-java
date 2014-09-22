package io.nextweb.operations;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.common.LocalServer;
import io.nextweb.common.LoginResult;
import io.nextweb.common.Postbox;
import io.nextweb.promise.BasicPromise;
import io.nextweb.promise.NextwebPromise;
import de.mxro.fn.Success;
import de.mxro.fn.SuccessFail;

public interface SessionOperations {

    /**
     * <p>
     * Close the session and release all associated resources.
     * </p>
     * <p>
     * All pending synchronizations will be completed before the closing is
     * finalized.
     * </p>
     * <p>
     * This operation is executed eagerly (see <a
     * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
     * and Eager operations</a>).
     * </p>
     * 
     * @return
     */
    public NextwebPromise<Success> close();

    /**
     * <p>
     * This operation is executed eagerly (see <a
     * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
     * and Eager operations</a>).
     * </p>
     * 
     * @return
     */
    public NextwebPromise<Success> commit();

    /**
     * To use links origination in other sessions.
     * 
     * @param link
     * @return
     */
    @Deprecated
    public Link node(Link link);

    /**
     * To use nodes originating in other sessions.
     * 
     * @param node
     * @return
     */
    @Deprecated
    public Link node(Node node);

    @Deprecated
    public Link node(String uri);

    @Deprecated
    public Link node(String uri, String secret);

    public Link link(Link link);

    public Link link(Node node);

    /**
     * <p>
     * Allows to define a {@link Link} to a node by supplying an address to this
     * node.
     * 
     * @param uri
     *            A URI to a Nextweb node.
     * @return A new {@link Link} object pointing to the node with the specified
     *         address.
     * @see <a
     *      href="http://nextweb.io/docs/nextweb-node-operation.value.html">Link
     *      Operation (Nextweb API Documentation)</a>
     */

    public Link link(String uri);

    /**
     * <p>
     * Allows to define a link to a node which is protected by a secret.
     * <p>
     * The specified secret will be used for both read and write operations.
     * 
     * @param uri
     *            A URI to a Nextweb node.
     * @param secret
     *            The access secret to be used to interact with the node.
     * @return A new {@link Link} object pointing to the node with the specified
     *         address.
     * @see <a
     *      href="http://nextweb.io/docs/nextweb-node-operation.value.html">Link
     *      Operation (Nextweb API Documentation)</a>
     */
    public Link link(String uri, String secret);

    public Session getAll(BasicPromise<?>... results);

    public NextwebPromise<SuccessFail> getAll(boolean asynchronous, BasicPromise<?>... results);

    /**
     * <p>
     * Creates a new node on the remote partner which does not have a direct
     * parent.
     * 
     * @see <a href="http://nextweb.io/docs/nextweb-seed.value.html">Seed
     *      Operation (Nextweb API Documentation)</a>
     * 
     * @return A {@link Query} which resolves to a new seed node if successful.
     */
    public Query seed();

    /**
     * <p>
     * Creates a new node without a direct parent on the {@link LocalServer}
     * specified.
     * 
     * @param server
     *            A local server instance.
     * @return A query which can be resolved to new node on the server.
     */
    public Query seed(LocalServer server);

    /**
     * <p>
     * Creates a new seed by trying to connect to the supplied HTTP service.
     * <p>
     * Note: When <code>"local"</code> is given as value for
     * <code>seedRpc</code>, then it is attempted to create a new seed on the
     * most recently created local server.
     * 
     * @param seedRpc
     *            The URI of a HTTP service which allows to create seed nodes.
     * @return A query which can be resolved to a new seed node.
     */
    public Query seed(String seedRpc);

    public Query createRealm(String realmTitle, String realmType, String apiKey);

    public NextwebPromise<Postbox> createPostbox(String realmTitle, String postboxType, String apiKey);

    /**
     * <p>
     * Append a value to a node. Only requires write rights.
     * </p>
     * <p>
     * This operation is executed eagerly (see <a
     * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
     * and Eager operations</a>).
     * </p>
     * 
     * @param value
     * @param toUri
     * @param secret
     * @return
     */
    public NextwebPromise<Success> post(Object value, String toUri, String secret);

    /**
     * <p>
     * This operation allows to login a user identified by an e-mail address and
     * password with the default application for this API.
     * </P>
     * <p>
     * If <a href="http://appjangle.com">Appjangle</a> is used, the default
     * application will be the Appjangle platform.
     * </p>
     * 
     * @param email
     *            The e-mail address, which identifies the user to be logged in.
     * @param password
     *            The password of the user to be logged in.
     * @return
     */
    public LoginResult login(String email, String password);

    /**
     * <p>
     * This operation allows to login a user identified by an e-mail address and
     * password with a specific application.
     * </P>
     * <p>
     * The application is identified by a link. At the given URI, a node
     * describing sufficient information about the application to manage users
     * for this application. No read/write access for the application node is
     * required.
     * </p>
     * <p>
     * If <a href="http://appjangle.com">Appjangle</a> is used, it is sufficient
     * to provide an arbitrary node with any content.
     * </p>
     * 
     * @param email
     *            The e-mail address, which identifies the user to be logged in.
     * @param password
     *            The password of the user to be logged in.
     * @param application
     *            This {@link Link} should point to a node, describing enough
     *            information to identify the application.
     * @return
     */
    public LoginResult login(String email, String password, Link application);

    /**
     * <p>
     * This operation allows to login a user identified by a session with the
     * default application for this API.
     * </p>
     * <p>
     * Session ids can be obtained by first performing a login operation with
     * e-mail and password.
     * </p>
     * <p>
     * If <a href="http://appjangle.com">Appjangle</a> is used, the default
     * application will be the Appjangle platform.
     * </p>
     * 
     * @param sessionId
     *            This {@link String} should contain a valid session id, which
     *            has been return by a proceeding login operation.
     * @return
     */
    public LoginResult login(String sessionId);

    /**
     * <p>
     * This operation allows to login a user identified by a session with for a
     * specific application.
     * </p>
     * <p>
     * Session ids can be obtained by first performing a login operation with
     * e-mail and password.
     * </p>
     * <p>
     * The application is identified by a link. At the given URI, a node
     * describing sufficient information about the application to manage users
     * for this application. No read/write access for the application node is
     * required.
     * </p>
     * 
     * @param sessionId
     *            This {@link String} should contain a valid session id, which
     *            has been return by a proceeding login operation.
     * @param application
     *            This {@link Link} should point to a node, describing enough
     *            information to identify the application.
     * @return
     */
    public LoginResult login(String sessionId, Link application);

    /**
     * <p>
     * The register operation allows to register a user for an application. If
     * no <code>application</code> is specified the user will be registered for
     * the default application of a platform.
     * </p>
     * <p>
     * If the <a href="http://appjangle.com">Appjangle platform</a> is used, the
     * default application will be the Appjangle platform itself. However,
     * unlimited custom application can be created by identifying nodes as
     * applications. Currently, any arbitrary node can be utilized as node to
     * identify an application.
     * </p>
     * 
     * @param email
     * @param password
     * @param application
     * @return
     */
    public LoginResult register(String email, String password, Link application);

    /**
     * <p>
     * The register operation allows to register a user for an application. If
     * no <code>application</code> is specified the user will be registered for
     * the default application of a platform.
     * </p>
     * <p>
     * If the <a href="http://appjangle.com">Appjangle platform</a> is used, the
     * default application will be the Appjangle platform itself. However,
     * unlimited custom application can be created by identifying nodes as
     * applications. Currently, any arbitrary node can be utilized as node to
     * identify an application.
     * </p>
     * 
     * @param email
     * @param password
     * @param application
     * @return
     */
    public LoginResult register(String email, String password);

}
