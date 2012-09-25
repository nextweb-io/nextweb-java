package io.nextweb.fn;

public class Fn {

	public static final ExceptionResult exception(final Object origin,
			final Throwable t) {
		return new ExceptionResult() {

			@Override
			public Object origin() {

				return origin;
			}

			@Override
			public Throwable exception() {

				return t;
			}
		};
	}

}
