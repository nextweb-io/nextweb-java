package io.nextweb.common;

public enum Interval {
	/**
	 * Update in realm time (every ~ms). Use only if strictly requires since
	 * this option can lead to significant CPU usage on the client and traffic
	 * to and from the server.
	 */
	REAL_TIME(200),
	/**
	 * Updates every ~400 ms, also might create significant CPU usage and
	 * traffic.
	 */
	EXTRA_FAST(400),
	/**
	 * Updates every second, this is the required setting information displayed
	 * on user interfaces.
	 */
	FAST(1000), MODERATE(2000), FIVESECONDS(5000), TENSECONDS(10000), MINUTELY(
			60 * 1000), HOURLY(60 * 60 * 1000), DAILY(24 * 60 * 60 * 1000),
	/**
	 * Only update when node is accessed.
	 */
	LAZY(-1);

	private final int milliseconds;

	public int getMilliseconds() {
		return milliseconds;
	}

	Interval(final int milliseconds) {
		this.milliseconds = milliseconds;
	}
}
