package com.ononedb.nextweb.local.jre;

import io.nextweb.Session;
import io.nextweb.common.LocalServer;
import io.nextweb.engine.StartServerCapability;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.operations.callbacks.Callback;

import java.util.HashMap;
import java.util.Map;

import mx.gwtutils.MxroGWTUtils;
import nx.auth.NxAuth;
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
import nx.server.realm.NxServerRealm;
import nx.server.realm.RealmCreatorConfiguration;
import nx.server.realm.RealmRoot;
import nx.server.realm.RegisteredRealmsService;
import nx.server.realm.RegisteredRealmsService.When.AddressProvided;
import nx.server.realm.RegisteredRealmsService.When.RealmRegistered;
import nx.server.seed.NxServerSeed;
import nx.server.seed.SeedHandler;
import nx.sync.NxSync;
import nx.versions.NxVersions;
import nx.versions.VersionedNetwork;
import one.client.jre.OneJre;
import one.utils.OneUtilsCollections.Predicate;
import one.utils.jre.OneUtilsJre;
import one.utils.server.ShutdownCallback;

import com.ononedb.nextweb.OnedbNextwebEngine;
import com.ononedb.nextweb.jre.Onedb;
import com.ononedb.nextweb.local.OnedbLocalServer;

public class OnedbStartServerJre implements StartServerCapability {

	@Override
	public LocalServer startServer(final int port) {

		final Onedb engine = Onedb.assertInitialized();

		StoppableRemoteConnection server;

		final VersionedNetwork serverNetwork = NxVersions.newVersionedNetwork();

		final StoppableRemoteConnection serverConnection = NxServerRealm
				.createStrictAuthorizedAndRealmTestServer(port,
						NxSync.connectTo(serverNetwork),
						OneUtilsJre.newJreConcurrency());

		final StoppableRemoteConnection connClosed = serverConnection;

		server = NxServer.forkConnection(serverConnection, NxServerRealm
				.createRealmServer(new RealmCreatorConfiguration() {

					@SuppressWarnings("serial")
					@Override
					public Map<String, String> getNewNodesRoots() {

						return new HashMap<String, String>() {
							{
								put("local", "http://localhost:" + port + "/");
							}
						};
					}

					private volatile int count = 0;

					@Override
					public RegisteredRealmsService getRealmDBService() {

						return new RegisteredRealmsService() {

							@Override
							public void stop(final ShutdownCallback callback) {
								callback.onShutdownComplete();
							}

							@Override
							public void registerRealm(
									final RealmRoot realmRoot,
									final RealmRegistered callback) {
								callback.thenDo();
							}

							@Override
							public void getUniqueRealmAddress(
									final String baseUri, final String title,
									final AddressProvided callback) {
								count++;

								callback.thenDo(baseUri + "r" + count + "/"
										+ MxroGWTUtils.getSimpleName(title, 6));
							}
						};
					}

					@Override
					public String getSecret() {
						return "myverysecretputsecret";
					}

					@Override
					public RealmRemoteConnectionFactory getRemoteConnectionFactory() {
						return new RealmRemoteConnectionFactory() {

							@Override
							public StoppableRemoteConnection createConnection() {
								return connClosed;
							}
						};
					}

				}), new Predicate<RemoteMessage>() {

			@Override
			public boolean testElement(final RemoteMessage element) {
				return NxRemoteUtils.isRequestRealmMessage(element);
			}
		});

		// to catch cache messages, which are otherwise handled by url
		// connections
		server = NxRemote.pullCachingConnection(200, server);

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
		server = NxServerSeed.newSeedHandlingConnection(
				OneUtilsJre.newJreConcurrency(), handler, server);

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
		OneJre.getJreSettings().addConnectionDecorator(localServerDecorator);

		return new OnedbLocalServer() {

			@Override
			public Result<Success> shutdown() {

				final AsyncResult<Success> asyncResult = new AsyncResult<Success>() {

					@Override
					public void get(final Callback<Success> callback) {
						OneJre.getJreSettings().removeConnectionDecorator(
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

				final Result<Success> shutdownResult = engine.createResult(
						engine.getExceptionManager(), null, asyncResult);

				shutdownResult.get(Fn.doNothing(Success.class));

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
		};
	}

}
