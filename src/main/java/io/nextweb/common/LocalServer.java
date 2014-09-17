package io.nextweb.common;

import io.nextweb.engine.NextwebEngine;
import io.nextweb.promise.NextwebPromise;
import de.mxro.fn.Success;

public interface LocalServer {

    public NextwebEngine engine();

    public NextwebPromise<Success> shutdown();

}
