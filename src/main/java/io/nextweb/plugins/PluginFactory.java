package io.nextweb.plugins;

public interface PluginFactory<PluginType> {

	public PluginType create(Object forObject);

}
