package io.nextweb.plugins;

public interface HasPlugins<ForType> {

	public <PluginType extends Plugin<?>> PluginType plugin(
			final PluginFactory<?, ? extends PluginType> factory);

}
