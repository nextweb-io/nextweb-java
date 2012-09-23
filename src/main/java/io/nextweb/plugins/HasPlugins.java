package io.nextweb.plugins;

public interface HasPlugins<ForType> {

	public <GType extends ForType, PluginType extends Plugin<GType>> PluginType plugin(
			PluginFactory<GType, PluginType> factory);

}
