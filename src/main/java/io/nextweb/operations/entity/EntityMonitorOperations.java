package io.nextweb.operations.entity;

import io.nextweb.Node;
import io.nextweb.common.Interval;
import io.nextweb.common.Monitor;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;

public interface EntityMonitorOperations {

	public Result<Monitor> monitor(Interval interval, Closure<Node> whenChanged);

}
