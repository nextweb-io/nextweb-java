package com.ononedb.nextweb.local.js;

import io.nextweb.Session;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;
import nx.auth.NxAuth;
import nx.client.gwt.GwtConcurrency;
import nx.core.Nodes;
import nx.core.Nx;
import nx.remote.NxRemote;
import nx.remote.RemoteConnectionDecorator;
import nx.remote.StoppableRemoteConnection;
import nx.remote.grammars.SeedCallback;
import nx.remote.messages.RemoteMessage;
import nx.remote.messages.RemoteNetworkMessage;
import nx.remote.messages.realm.RequestRealmMessage;
import nx.remote.messages.seed.SeedMessage;
import nx.remote.utils.NxRemoteUtils;
import nx.server.NxServer;
import nx.server.seed.NxServerSeed;
import nx.server.seed.SeedHandler;
import nx.sync.NxSync;
import nx.versions.NxVersions;
import nx.versions.VersionedNetwork;
import one.client.gwt.OneGwt;
import one.utils.OneUtilsCollections.Predicate;
import one.utils.server.ShutdownCallback;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.js.OnedbNextwebJsEngineImpl;
import com.ononedb.nextweb.local.OnedbLocalServer;

@ExportPackage("")
@Export
public class OnedbLocal implements Exportable {

	@Export
	public static OnedbLocalServerJs init(final int port) {

		StoppableRemoteConnection server;

		final OnedbNextwebJsEngineImpl engine = OnedbNextwebJsEngineImpl
				.assertInitialized();

		final VersionedNetwork serverNetwork = NxVersions.newVersionedNetwork();

		final StoppableRemoteConnection serverConnection = NxServer
				.newBasicServer(NxSync.connectTo(serverNetwork),
						new GwtConcurrency());

		final StoppableRemoteConnection connClosed = serverConnection;

		// server = NxServer.forkConnection(serverConnection, NxServerRealm
		// .createRealmServer(new RealmCreatorConfiguration() {
		//
		// @SuppressWarnings("serial")
		// @Override
		// public Map<String, String> getNewNodesRoots() {
		//
		// return new HashMap<String, String>() {
		// {
		// put("local", "http://localhost:" + port + "/");
		// }
		// };
		// }
		//
		// private volatile int count = 0;
		//
		// @Override
		// public RegisteredRealmsService getRealmDBService() {
		//
		// return new RegisteredRealmsService() {
		//
		// @Override
		// public void stop(final ShutdownCallback callback) {
		// callback.onShutdownComplete();
		// }
		//
		// @Override
		// public void registerRealm(
		// final RealmRoot realmRoot,
		// final RealmRegistered callback) {
		// callback.thenDo();
		// }
		//
		// @Override
		// public void getUniqueRealmAddress(
		// final String baseUri, final String title,
		// final AddressProvided callback) {
		// count++;
		//
		// callback.thenDo(baseUri + "r" + count + "/"
		// + MxroGWTUtils.getSimpleName(title, 6));
		// }
		// };
		// }
		//
		// @Override
		// public String getSecret() {
		// return "myverysecretputsecret";
		// }
		//
		// @Override
		// public RealmRemoteConnectionFactory getRemoteConnectionFactory() {
		// return new RealmRemoteConnectionFactory() {
		//
		// @Override
		// public StoppableRemoteConnection createConnection() {
		// return connClosed;
		// }
		// };
		// }
		//
		// }), new Predicate<RemoteMessage>() {
		//
		// @Override
		// public boolean testElement(final RemoteMessage element) {
		// return NxRemoteUtils.isRequestRealmMessage(element);
		// }
		// });

		// to catch cache messages, which are otherwise handled by url
		// connections
		server = NxRemote.pullCachingConnection(200, serverConnection);

		final SeedHandler handler = new SeedHandler() {

			private volatile int counter;

			@Override
			public void stop(final ShutdownCallback callback) {
				callback.onShutdownComplete();
			}

			@Override
			public void persistCounterValue(
					final WhenCounterValuePersisted callback) {
				callback.onSuccess();
			}

			@Override
			public void handle(final SeedCallback callback) {
				counter++;
				final String seedNodeUri = "http://localhost:" + port
						+ "/seeds/s" + counter;
				final Object seedNode = Nodes.define("s" + counter).at(
						seedNodeUri);

				Nx.put(seedNode).in(serverNetwork);
				final String secret = NxAuth.newRandomToken();
				Nx.append(NxAuth.readWriteToken(secret)).to(seedNode)
						.in(serverNetwork);

				callback.onSuccess(seedNodeUri, secret);
			}
		};
		server = NxServerSeed.newSeedHandlingConnection(new GwtConcurrency(),
				handler, server);

		final StoppableRemoteConnection serverClosed = server;

		final StoppableRemoteConnection unblockedServer = server;
		// ... so shutting down client will not shut down server
		server = NxRemote.createBlockShutdownsConnection(serverConnection);

		final RemoteConnectionDecorator localServerDecorator = new RemoteConnectionDecorator() {

			@Override
			public StoppableRemoteConnection decorate(
					final StoppableRemoteConnection connection) {
				return NxServer.forkConnection(connection, NxRemote
						.ignorePullCachingConnection(NxRemote
								.createBlockShutdownsConnection(serverClosed)),

				new Predicate<RemoteMessage>() {

					@Override
					public boolean testElement(final RemoteMessage message) {

						if (NxRemoteUtils.isNetworkMessage(message)) {
							final RemoteNetworkMessage networkMessage = NxRemoteUtils
									.asNetworkMessage(message);

							return networkMessage
									.getNode()
									.getURI()
									.startsWith(
											"http://localhost:" + port + "/");
						}

						if (NxRemoteUtils.isRequestRealmMessage(message)) {

							final RequestRealmMessage requestRealmMessage = NxRemoteUtils
									.asRequestRealmMessage(message);
							// System.out.println("Testing: "
							// + requestRealmMessage.getRealmKind());
							return requestRealmMessage.getRealmKind().equals(
									"local");

						}

						if (NxRemoteUtils.isRequestSeedMessage(message)) {
							final SeedMessage seedMessage = NxRemoteUtils
									.asRequestSeedMessage(message);

							return seedMessage.getSeedType().equals("local");
						}

						return false;

					}
				});
			}
		};
		OneGwt.getSettings().addConnectionDecorator(localServerDecorator);

		return OnedbLocalServerJs.wrap(new OnedbLocalServer() {

			@Override
			public Result<Success> shutdown() {

				final AsyncResult<Success> asyncResult = new AsyncResult<Success>() {

					@Override
					public void get(final Callback<Success> callback) {
						OneGwt.getSettings().removeConnectionDecorator(
								localServerDecorator);

						unblockedServer.stop(new ShutdownCallback() {

							@Override
							public void onShutdownComplete() {
								callback.onSuccess(Success.INSTANCE);
							}

							@Override
							public void onFailure(final Throwable t) {
								callback.onFailure(Fn.exception(this, t));
							}
						});
					}
				};

				assert engine != null : "Engine not initialized";
				assert engine.getExceptionManager() != null : "Exception manager not defined.";

				final Result<Success> shutdownResult = engine.createResult(
						engine.getExceptionManager(), null, asyncResult);

				shutdownResult.get(new Closure<Success>() {

					@Override
					public void apply(final Success o) {
						// nothing
					}
				});

				return shutdownResult;
			}

			@Override
			public Session createSession() {
				return engine.createSession();
			}

			@Override
			public OnedbNextwebEngine getEngine() {

				return engine;
			}
		});

	}

	public OnedbLocal() {
		super();
	}

}
