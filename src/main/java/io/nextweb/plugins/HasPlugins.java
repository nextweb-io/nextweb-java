package io.nextweb.plugins;

public interface HasPlugins {
	
	public <PluginType extends Plugin> PluginType plugin(PluginFactory<PluginType> factory);
	
}
