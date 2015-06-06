package io.nextweb.nodes;

/**
 * Defines a reference to a node (consisting of the nodes reference and the
 * secret required to access the node).
 * 
 * @author Max
 * 
 */
public interface Reference {

    public String uri();

    public String getSecret();

}
