package io.nextweb;

import io.nextweb.operations.EntityOperations;
import io.nextweb.plugins.HasPlugins;

public interface Link extends HasPlugins, EntityOperations {

	public String getUri();

	public String uri();

}
