package io.nextweb.operations.entity;

import io.nextweb.common.Interval;
import io.nextweb.common.Monitor;
import io.nextweb.common.MonitorContext;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Result;

public interface EntityMonitorOperations {

	/**
	 * <p>
	 * Monitor a node for changes in a given interval.</
	 * <p>
	 * This operation is executed eagerly (see <a
	 * href="http://nextweb.io/docs/nextweb-eager-operations.value.html">Lazy
	 * and Eager operations</a>).
	 * </p>
	 * 
	 * @param interval
	 * @param whenChanged
	 * @return
	 */
	public Result<Monitor> monitor(Interval interval,
			Closure<MonitorContext> whenChanged);

}
