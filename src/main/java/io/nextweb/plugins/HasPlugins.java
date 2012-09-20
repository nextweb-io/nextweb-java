package io.nextweb.plugins;

public interface HasPlugins {

	public <ForType, PluginType extends Plugin<ForType>> PluginType plugin(
			PluginFactory<ForType, PluginType> factory);

}
