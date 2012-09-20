package io.nextweb.plugins;

import io.nextweb.Entity;

public interface DefaultPluginFactory {

	public PluginFactory<? extends Entity, ? extends Entity_SelectPlugin<?>> select();

}
