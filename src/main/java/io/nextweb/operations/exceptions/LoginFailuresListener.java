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
	 * Called if it is attempted to register a user, who's email address has
	 * already been registered.
	 */
	public void onUserAlreadyRegistered();

	/**
	 * Returned when a user needs to confirm an additional challenge (e.g.
	 * verify human) to finalize registration/login.
	 * 
	 * @param cr
	 */
	public void onChallenged(ChallengedResult cr);

}
