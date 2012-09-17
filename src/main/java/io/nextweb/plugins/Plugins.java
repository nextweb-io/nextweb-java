package io.nextweb.plugins;

public class Plugins {

	public static <PluginType extends Plugin> PluginType plugin(
			Object forObject, PluginFactory<PluginType> factory) {

		return factory.create(forObject);

	}

}
