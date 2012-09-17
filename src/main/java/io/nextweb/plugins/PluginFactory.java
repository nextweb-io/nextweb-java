package io.nextweb.plugins;

public interface PluginFactory<PluginType extends Plugin> {

	public PluginType create(Object forObject);

}
