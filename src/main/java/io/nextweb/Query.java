package io.nextweb;

import io.nextweb.operations.EntityOperations;
import io.nextweb.operations.EntityRequestOperations;
import io.nextweb.plugins.HasPlugins;

public interface Query extends HasPlugins, EntityOperations,
		EntityRequestOperations {

}
