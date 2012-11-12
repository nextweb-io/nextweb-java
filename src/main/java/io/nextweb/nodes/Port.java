package io.nextweb.nodes;

/**
 * Defines an access pathway to a node (consisting of the nodes reference and
 * the secret {@link Token} required to access the node.
 * 
 * @author Max
 * 
 */
public interface Port {

	public String getUri();

	public String getSecret();

}
