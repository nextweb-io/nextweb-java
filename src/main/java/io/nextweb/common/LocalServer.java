package io.nextweb.common;

import de.mxro.fn.Success;
import io.nextweb.promise.NextwebPromise;

public interface LocalServer {

	public NextwebPromise<Success> shutdown();

}
