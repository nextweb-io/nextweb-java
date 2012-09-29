package io.nextweb.operations;

import io.nextweb.Monitor;
import io.nextweb.Node;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;

public interface EntityMonitorOperations {

	public Result<Monitor> monitor(Closure<Node> whenChanged);

}
