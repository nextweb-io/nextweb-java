package io.nextweb.operations.exceptions;

import io.nextweb.Link;

/**
 * Returned when a user needs to confirm an additional challenge (e.g. verify
 * human) to finalize registration/login.
 * 
 * @author mroh004
 * 
 */
public interface ChallengedResult {

	/**
	 * 
	 * @return The node where details of the challenge can be found.
	 */
	public Link challengeLink();

}
