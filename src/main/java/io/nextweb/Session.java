package io.nextweb;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.operations.SessionOperations;
import io.nextweb.operations.exceptions.ExceptionManager;
import io.nextweb.plugins.HasPlugins;

public interface Session extends SessionOperations, HasPlugins<Session> {

	public NextwebEngine getEngine();

	public ExceptionManager getExceptionManager();

}
