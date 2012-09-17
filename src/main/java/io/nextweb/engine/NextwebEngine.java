package io.nextweb.engine;

import io.nextweb.Session;

public interface NextwebEngine {

	public Session createSession();

	public void unhandledException(Object context, Throwable t);

}
