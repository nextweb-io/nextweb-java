package com.ononedb.nextweb.common;

import io.nextweb.Entity;
import io.nextweb.Session;
import io.nextweb.operations.exceptions.AuthorizationExceptionResult;
import io.nextweb.plugins.DefaultPluginFactory;
import io.nextweb.plugins.Entity_SelectPlugin;
import io.nextweb.plugins.PluginFactory;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;

import com.ononedb.nextweb.OnedbObject;
import com.ononedb.nextweb.internal.OnedbFactory;
import com.ononedb.nextweb.plugins.EntityPlugin_Select_Factory;

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

	public static OneClient client(OnedbObject fromObj) {
		return fromObj.getOnedbSession().getClient();
	}

	public static OnedbFactory factory(OnedbObject fromObj) {
		return fromObj.getOnedbSession().getFactory();
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

	public static DefaultPluginFactory onedbDefaultPluginFactory() {
		return new DefaultPluginFactory() {

			@SuppressWarnings("unchecked")
			@Override
			public <GEntity extends Entity, GPlugin extends Entity_SelectPlugin<GEntity>> PluginFactory<GEntity, GPlugin> select() {
				return (PluginFactory<GEntity, GPlugin>) EntityPlugin_Select_Factory.FACTORY;
			}

		};

	}

}
