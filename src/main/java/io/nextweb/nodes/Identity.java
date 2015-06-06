package io.nextweb.nodes;

/**
 * Defines the identity of a node (consisting of the nodes reference and the
 * secret {@link Token} required to access the node.
 * 
 * @author Max
 * 
 */
public interface Identity {

    public String getUri();

    public String getSecret();

}
