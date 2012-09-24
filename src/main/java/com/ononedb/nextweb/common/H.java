package com.ononedb.nextweb.common;

import io.nextweb.Entity;
import io.nextweb.EntityList;
import io.nextweb.Node;
import io.nextweb.Session;
import io.nextweb.fn.Closure;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.plugins.PluginFactory;
import io.nextweb.plugins.core.DefaultPluginFactory;
import io.nextweb.plugins.core.Plugin_EntityList_Select;
import io.nextweb.plugins.core.Plugin_Entity_ClearVersions;
import io.nextweb.plugins.core.Plugin_Entity_Remove;
import io.nextweb.plugins.core.Plugin_Entity_Select;
import io.nextweb.plugins.core.Plugin_Node_Append;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.nodes.OneTypedReference;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.OnedbObject;
import com.ononedb.nextweb.OnedbSession;
import com.ononedb.nextweb.internal.OnedbFactory;
import com.ononedb.nextweb.plugins.P_EntityList_Select_Factory;
import com.ononedb.nextweb.plugins.P_Entity_ClearVersions_Factory;
import com.ononedb.nextweb.plugins.P_Entity_Remove_Factory;
import com.ononedb.nextweb.plugins.P_Entity_Select_Factory;
import com.ononedb.nextweb.plugins.P_Node_Append_Factory;

/**
 * Helper methods.
 * 
 * @author mroh004
 * 
 */
public class H {

	public static CoreDsl dsl(OnedbObject fromObj) {
		return client(fromObj).one();
	}

	public static <N extends Entity, E extends EntityList<?>> void each(
			EntityList<E> entity, Iterable<N> list, final Closure<Node> f) {
		for (N e : list) {
			e.get(CallbackFactory.lazyCallback(entity.getSession(),
					entity.getExceptionManager(), new Closure<Node>() {

						@Override
						public void apply(Node o) {
							f.apply(o);
						}

					}));

		}
	}

	public static OneClient client(OnedbObject fromObj) {
		return session(fromObj).getClient();
	}

	public static OneTypedReference<?> node(CoreDsl dsl, Node node) {
		return dsl.reference(node.getUri());
	}

	public static OnedbFactory factory(OnedbObject fromObj) {
		return session(fromObj).getFactory();
	}

	public static final OnedbSession session(OnedbObject fromObj) {
		return fromObj.getOnedbSession();
	}

	public static final OnedbNextwebEngine engine(OnedbObject fromObj) {
		return fromObj.getOnedbSession().getOnedbEngine();
	}

	public static DefaultPluginFactory plugins(Session session) {
		return session.getEngine().plugin();
	}

	public static AuthorizationExceptionResult fromUnauthorizedContext(
			final WithUnauthorizedContext context) {
		return new AuthorizationExceptionResult() {

			@Override
			public Object getType() {
				return context.cause();
			}

			@Override
			public String getMessage() {
				return context.message();
			}
		};
	}

	@SuppressWarnings("unchecked")
	public static DefaultPluginFactory onedbDefaultPluginFactory() {
		return new DefaultPluginFactory() {

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_Select<GEntity>> PluginFactory<GEntity, GPlugin> select() {
				return (PluginFactory<GEntity, GPlugin>) P_Entity_Select_Factory.FACTORY;
			}

			@Override
			public <GEntity extends EntityList<?>, GPlugin extends Plugin_EntityList_Select<GEntity>> PluginFactory<GEntity, GPlugin> selectForLists() {
				return (PluginFactory<GEntity, GPlugin>) P_EntityList_Select_Factory.FACTORY;
			}

			@Override
			public <GEntity extends Node, GPlugin extends Plugin_Node_Append<GEntity>> PluginFactory<GEntity, GPlugin> appendForNode() {

				return (PluginFactory<GEntity, GPlugin>) P_Node_Append_Factory.FACTORY;
			}

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_Remove<GEntity>> PluginFactory<GEntity, GPlugin> remove() {

				return (PluginFactory<GEntity, GPlugin>) P_Entity_Remove_Factory.FACTORY;

			}

			@Override
			public <GEntity extends Entity, GPlugin extends Plugin_Entity_ClearVersions<GEntity>> PluginFactory<GEntity, GPlugin> clearVersions() {

				return (PluginFactory<GEntity, GPlugin>) P_Entity_ClearVersions_Factory.FACTORY;
			}

		};

	}

}
