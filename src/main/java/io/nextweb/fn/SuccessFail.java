package io.nextweb.fn;

public abstract class SuccessFail {

	public abstract boolean isSuccess();

	public abstract boolean isFail();

	public abstract Throwable getException();

	private static final SuccessFail SUCCESS = new SuccessFail() {

		@Override
		public boolean isSuccess() {
			return true;
		}

		@Override
		public boolean isFail() {
			return false;
		}

		@Override
		public Throwable getException() {
			throw new IllegalStateException(
					"Success does not have an exception.");
		}

		@Override
		public String toString() {

			return "SucessFail.SUCCESS";
		}

	};

	public static final SuccessFail success() {
		return SUCCESS;
	}

	public static final SuccessFail fail(final Throwable t) {
		return new SuccessFail() {

			@Override
			public boolean isSuccess() {
				return false;
			}

			@Override
			public boolean isFail() {
				return true;
			}

			@Override
			public Throwable getException() {

				return t;
			}

			@Override
			public String toString() {

				return "SucessFail.FAIL(" + t.getMessage() + ")";
			}
		};
	}

}
