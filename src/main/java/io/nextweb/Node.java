package io.nextweb;

import io.nextweb.operations.entity.EntityRequestOperations;

/**
 * <p>
 * A resolved node in a Nextweb graph.
 * 
 * @see <a href="http://nextweb.io/docs/nextweb-entity.value.html">Entities
 *      (Nextweb API Documentation)</a>
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public interface Node extends Entity, EntityRequestOperations<Node> {

    public String getUri();

    public String uri();

    public String getSecret();

    public String secret();

    public Object value();

    public Object getValue();

    /**
     * The outgoing connections of this node.
     * 
     * @return
     */
    public LinkList children();

    /**
     * <p>
     * By default, Nodes are only instantiated if they exist. However, this
     * method can return false if the node has been removed after it has been
     * loaded.
     * </p>
     * 
     * @return
     */
    public boolean exists();

    public <ValueType> ValueType value(Class<ValueType> type);

    public <Type> Type as(final Class<Type> type);

}
