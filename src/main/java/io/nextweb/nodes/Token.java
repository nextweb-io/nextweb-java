package io.nextweb.nodes;

/**
 * A security authorization.
 * 
 * @author Max
 * 
 */
public interface Token {

	public boolean hasSecret();

	public String getSecret();

	public String getIdentification();

	public String getGrantedAuthorization();

}
