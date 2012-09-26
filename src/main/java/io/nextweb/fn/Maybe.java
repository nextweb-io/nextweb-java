package io.nextweb.fn;

import java.util.ArrayList;
import java.util.List;

public abstract class Maybe<V> {

	public abstract boolean is();

	public abstract V value();

	public static <V> List<V> allValues(List<Maybe<V>> list) {
		List<V> val = new ArrayList<V>(list.size());

		for (Maybe<V> m : list) {
			if (m.is()) {
				val.add(m.value());
			}
		}

		return val;
	}

	public static <V> Maybe<V> is(final V value) {
		return new Maybe<V>() {

			@Override
			public boolean is() {
				return true;
			}

			@Override
			public V value() {
				return value;
			}
		};
	}

	public static <V> Maybe<V> isNot(Class<V> clazz) {
		return new Maybe<V>() {

			@Override
			public boolean is() {
				return false;
			}

			@Override
			public V value() {
				throw new IllegalStateException(
						"Cannot access value of Maybe if it is not.");
			}
		};
	}

}
