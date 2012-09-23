package io.nextweb.operations.callbacks;

public interface CallbackEntry {

	public boolean hasEagerFailureListener();

	public boolean hasEagerUndefinedListener();

	public boolean hasEagerUnauthorizedListener();

}
