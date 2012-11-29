package io.nextweb.operations.entity;

import io.nextweb.common.Interval;
import io.nextweb.common.Monitor;
import io.nextweb.common.MonitorContext;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;

public interface EntityMonitorOperations {

	public Result<Monitor> monitor(Interval interval,
			Closure<MonitorContext> whenChanged);

}
