package io.nextweb.fn;

public interface ReadOnlyList<E> extends Iterable<E> {

	public int size();

	public boolean isEmpty();

	public boolean contains(Object o);

	public E get(int index);

}
