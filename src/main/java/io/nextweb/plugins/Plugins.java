package io.nextweb.plugins;

public class Plugins {

	public static <ForType, PluginType extends Plugin<ForType>> PluginType plugin(
			Object forObject, PluginFactory<ForType, PluginType> factory) {

		return factory.create(forObject);

	}

}
