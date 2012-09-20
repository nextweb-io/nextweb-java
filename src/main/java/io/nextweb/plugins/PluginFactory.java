package io.nextweb.plugins;

public interface PluginFactory<ForType, PluginType extends Plugin<ForType>> {

	public PluginType create(ForType forObject);

}
