package io.nextweb.plugins;

public class Plugins {

	public static <ForType, PluginType extends Plugin<ForType>> PluginType plugin(
			ForType forObject, PluginFactory<ForType, PluginType> factory) {

		return factory.create(forObject);

	}

}
