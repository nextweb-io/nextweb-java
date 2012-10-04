package io.nextweb.common;

import io.nextweb.Link;

public interface User extends LoginResponse {

	/**
	 * 
	 * @return A link to the node where all data of this user is stored.
	 */
	public Link userNode();

	/**
	 * 
	 * @return The e-mail address this user used to register.
	 */
	public String email();

	/**
	 * 
	 * @return A unique session token, which can be used to log in at a later
	 *         point in time. Session tokens have limited validity (for instance
	 *         2 weeks).
	 */
	public String sessionToken();

}
