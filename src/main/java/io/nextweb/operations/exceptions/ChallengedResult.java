package io.nextweb.operations.exceptions;

/**
 * Returned when a user needs to confirm an additional challenge (e.g. verify
 * human) to finalize registration/login.
 * 
 * @author mroh004
 * 
 */
public interface ChallengedResult {

	public Link challengeLink();

}
