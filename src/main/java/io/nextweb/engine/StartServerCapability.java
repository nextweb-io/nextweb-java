package io.nextweb.engine;

import io.nextweb.common.LocalServer;

public interface StartServerCapability extends Capability {

    public LocalServer startServer(String domain);

}
