package io.nextweb.operations.exceptions;

public interface LoginFailuresListener {

	/**
	 * Called when username / password or session id wrong
	 */
	public void onInvalidDetails();

	/**
	 * Called if user is known to onedb but not for the specified application.
	 */
	public void onNotRegisteredForApplication();

	/**
	 * Returned when a user needs to confirm an additional challenge (e.g.
	 * verify human) to finalize registration/login.
	 * 
	 * @param cr
	 */
	public void onChallenged(ChallengedResult cr);

}
