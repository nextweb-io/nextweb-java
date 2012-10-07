package com.ononedb.nextweb.common;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.Node;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.ImpossibleResult;
import io.nextweb.operations.exceptions.UnauthorizedResult;
import io.nextweb.operations.exceptions.UndefinedResult;
import io.nextweb.plugins.PluginFactory;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.results.WithImpossibleContext;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.OnedbEntityList;
import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.OnedbObject;
import com.ononedb.nextweb.OnedbSession;
import com.ononedb.nextweb.internal.OnedbFactory;
import com.ononedb.nextweb.plugins.DefaultPluginFactory;
import com.ononedb.nextweb.plugins.Plugin_EntityList_Select;
import com.ononedb.nextweb.plugins.Plugin_EntityList_SetValue;
import com.ononedb.nextweb.plugins.Plugin_Entity_Append;
import com.ononedb.nextweb.plugins.Plugin_Entity_ClearVersions;
import com.ononedb.nextweb.plugins.Plugin_Entity_Monitor;
import com.ononedb.nextweb.plugins.Plugin_Entity_Remove;
import com.ononedb.nextweb.plugins.Plugin_Entity_Select;
import com.ononedb.nextweb.plugins.Plugin_Entity_SetValue;
import com.ononedb.nextweb.plugins.Plugin_Session_Core;
import com.ononedb.nextweb.plugins.impl.P_EntityList_Select_Factory;
import com.ononedb.nextweb.plugins.impl.P_EntityList_SetValue_Factory;
import com.ononedb.nextweb.plugins.impl.P_Entity_Append_Factory;
import com.ononedb.nextweb.plugins.impl.P_Entity_ClearVersions_Factory;
import com.ononedb.nextweb.plugins.impl.P_Entity_Monitor_Factory;
import com.ononedb.nextweb.plugins.impl.P_Entity_Remove_Factory;
import com.ononedb.nextweb.plugins.impl.P_Entity_Select_Factory;
import com.ononedb.nextweb.plugins.impl.P_Entity_SetValue_Factory;
import com.ononedb.nextweb.plugins.impl.P_Session_Core_Factory;

/**
 * Helper methods.
 * 
 * @author mroh004
 * 
 */
public class H {

	public static CoreDsl dsl(final OnedbObject fromObj) {
		return client(fromObj).one();
	}

	public static <N extends Entity, E extends EntityList> void each(
			final EntityList entity, final Iterable<N> list,
			final Closure<Node> f) {
		for (final N e : list) {
			e.get(CallbackFactory.lazyCallback(entity.getSession(),
					entity.getExceptionManager(), new Closure<Node>() {

						@Override
						public void apply(final Node o) {
							f.apply(o);
						}

					}));

		}
	}

	public static OneClient client(final OnedbObject fromObj) {
		return session(fromObj).getClient();
	}

	public static OneTypedReference<?> node(final CoreDsl dsl, final Node node) {
		return dsl.reference(node.getUri());
	}

	public static OnedbFactory factory(final OnedbObject fromObj) {
		return session(fromObj).getFactory();
	}

	public static OnedbFactory factory(final OnedbEntityList list) {
		return session(list).getFactory();
	}

	public static final OnedbSession session(final OnedbObject fromObj) {
		return fromObj.getOnedbSession();
	}

	public static final OnedbNextwebEngine engine(final OnedbObject fromObj) {
		return fromObj.getOnedbSession().getOnedbEngine();
	}

	public static DefaultPluginFactory plugins(final Session session) {
		return session.getEngine().plugin();
	}

	public static UnauthorizedResult fromUnauthorizedContext(
			final Object origin, final WithUnauthorizedContext context) {
		return new UnauthorizedResult() {

			@Override
			public Object getType() {
				return context.cause();
			}

			@Override
			public String getMessage() {
				return context.message();
			}

			@Override
			public Object origin() {
				return origin;
			}
		};
	}

	public static ImpossibleResult fromImpossibleContext(final Object origin,
			final WithImpossibleContext context) {

		return new ImpossibleResult() {

			@Override
			public Object origin() {
				return origin;
			}

			@Override
			public String message() {
				return context.message();
			}

			@Override
			public Object cause() {
				return context.cause();
			}

		};
	}

	@SuppressWarnings("unchecked")
	public static DefaultPluginFactory onedbDefaultPluginFactory() {
		return new DefaultPluginFactory() {

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_Select<GEntity>> PluginFactory<GEntity, GPlugin> select() {
				final Object factory = P_Entity_Select_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) factory;
			}

			@Override
			public <GEntity extends EntityList, GPlugin extends Plugin_EntityList_Select<GEntity>> PluginFactory<GEntity, GPlugin> selectForLists() {
				final Object factory = P_EntityList_Select_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) factory;
			}

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_Append<GEntity>> PluginFactory<GEntity, GPlugin> append() {

				final Object factory = P_Entity_Append_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) factory;
			}

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_Remove<GEntity>> PluginFactory<GEntity, GPlugin> remove() {
				final Object object = P_Entity_Remove_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) object;

			}

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_ClearVersions<GEntity>> PluginFactory<GEntity, GPlugin> clearVersions() {
				final Object object = P_Entity_ClearVersions_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) object;
			}

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_SetValue<GEntity>> PluginFactory<GEntity, GPlugin> setValue() {

				final Object object = P_Entity_SetValue_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) object;
			}

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_Monitor<GEntity>> PluginFactory<GEntity, GPlugin> monitor() {
				final Object object = P_Entity_Monitor_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) object;
			}

			@Override
			public <GSession extends Session, GPlugin extends Plugin_Session_Core<GSession>> PluginFactory<GSession, GPlugin> session() {
				final Object object = P_Session_Core_Factory.FACTORY;
				return (PluginFactory<GSession, GPlugin>) object;
			}

			@Override
			public <GEntity extends EntityList, GPlugin extends Plugin_EntityList_SetValue<GEntity>> PluginFactory<GEntity, GPlugin> setValueForLists() {
				final Object object = P_EntityList_SetValue_Factory.FACTORY;
				return (PluginFactory<GEntity, GPlugin>) object;
			}

		};

	}

	public static UndefinedResult createUndefinedResult(final Object forObj,
			final String uri) {
		return new UndefinedResult() {

			@Override
			public Object origin() {
				return forObj;
			}

			@Override
			public String message() {
				return "No node is defined at address: [" + uri + "]";
			}
		};
	}

}
