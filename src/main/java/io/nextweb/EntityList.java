package io.nextweb;

import io.nextweb.operations.EntityOperations;
import io.nextweb.plugins.HasPlugins;

import java.util.Collection;

public interface EntityList extends HasPlugins, EntityOperations,
		Collection<Object> {

}
