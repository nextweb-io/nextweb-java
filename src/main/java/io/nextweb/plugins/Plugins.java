package io.nextweb.plugins;

public class Plugins {

	@SuppressWarnings("unchecked")
	public static <ObjectType, PluginType> PluginType plugin(
			final ObjectType n,
			final PluginFactory<?, ? extends PluginType> factory) {
		final Object node = n;
		return (PluginType) ((PluginFactory<ObjectType, Plugin<ObjectType>>) factory)
				.create((ObjectType) node);
	}

	// @SuppressWarnings("unchecked")
	// public static <PluginType> PluginType plugin(final Entity n,
	// final PluginFactory<?, ? extends PluginType> factory) {
	// final Object node = n;
	// return (PluginType) ((PluginFactory<Entity, Plugin<Entity>>) factory)
	// .create((Entity) node);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public static <PluginType> PluginType plugin(final EntityList n,
	// final PluginFactory<?, ? extends PluginType> factory) {
	// final Object node = n;
	// return (PluginType) ((PluginFactory<EntityList, Plugin<EntityList>>)
	// factory)
	// .create((EntityList) node);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public static <PluginType> PluginType plugin(final LinkListQuery n,
	// final PluginFactory<?, ? extends PluginType> factory) {
	// final Object node = n;
	// return (PluginType) ((PluginFactory<LinkListQuery,
	// Plugin<LinkListQuery>>) factory)
	// .create((LinkListQuery) node);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public static <PluginType> PluginType plugin(final Session n,
	// final PluginFactory<?, ? extends PluginType> factory) {
	// final Object node = n;
	// return (PluginType) ((PluginFactory<Session, Plugin<Session>>) factory)
	// .create((Session) node);
	// }

}
