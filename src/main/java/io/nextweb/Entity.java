package io.nextweb;

import io.nextweb.fn.Result;
import io.nextweb.operations.EntityOperations;
import io.nextweb.plugins.HasPlugins;

public interface Entity extends HasPlugins, EntityOperations, Result<Node> {

}
