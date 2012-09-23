package io.nextweb.plugins;

public class Plugins {

	public static <GType, PluginType extends Plugin<GType>> PluginType plugin(
			GType forObj, PluginFactory<GType, PluginType> factory) {

		return factory.create(forObj);

	}

}
