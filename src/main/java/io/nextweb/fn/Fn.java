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

	public static <G> Closure<G> doNothing(final Class<G> inputType) {
		return new Closure<G>() {

			@Override
			public void apply(final G o) {
				// nada
			}
		};
	}

}
