package io.nextweb.plugins;

public interface HasPlugins<ForType> {

	public <PluginType extends Plugin<ForType>> PluginType plugin(
			PluginFactory<ForType, PluginType> factory);

}
