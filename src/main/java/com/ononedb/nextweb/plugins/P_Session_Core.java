package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.Session;
import io.nextweb.common.LoginResult;
import io.nextweb.common.Postbox;
import io.nextweb.common.User;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.BasicResult;
import io.nextweb.fn.Closure;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.fn.Fn;
import io.nextweb.fn.Result;
import io.nextweb.fn.Success;
import io.nextweb.fn.SuccessFail;
import io.nextweb.operations.callbacks.Callback;
import io.nextweb.operations.callbacks.CallbackFactory;
import io.nextweb.operations.callbacks.EagerCallback;
import io.nextweb.operations.exceptions.ChallengedResult;
import io.nextweb.operations.exceptions.UndefinedResult;
import io.nextweb.plugins.core.Plugin_Session_Core;

import java.io.Serializable;

import one.async.joiner.CallbackLatch;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenCommitted;
import one.core.dsl.callbacks.WhenMessagePosted;
import one.core.dsl.callbacks.WhenRealmCreated;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.WhenUserLoggedIn;
import one.core.dsl.callbacks.WhenUserRegistered;
import one.core.dsl.callbacks.results.WithChallengedContext;
import one.core.dsl.callbacks.results.WithCommittedResult;
import one.core.dsl.callbacks.results.WithMessagePostedResult;
import one.core.dsl.callbacks.results.WithQuotaExceededContext;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.dsl.callbacks.results.WithSeedResult;
import one.core.dsl.callbacks.results.WithTargetNodeDoesNotExistContext;
import one.core.dsl.callbacks.results.WithUnauthorizedContext;
import one.core.dsl.callbacks.results.WithUserRegisteredResult;
import one.core.dsl.grammars.LoginWithSessionIdParameters;
import one.core.dsl.grammars.LoginWithUserDetailsParameters;
import one.core.dsl.grammars.RegisterUserParameters;

import com.ononedb.nextweb.OnedbSession;
import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.internal.LoginResultImpl;

public class P_Session_Core implements Plugin_Session_Core<OnedbSession> {

	OnedbSession session;

	@Override
	public void injectObject(final OnedbSession obj) {
		session = obj;
	}

	@Override
	public Result<Success> commit() {
		final Result<Success> commitResult = session.getOnedbEngine()
				.createResult(session.getExceptionManager(), session,
						new AsyncResult<Success>() {

							@Override
							public void get(final Callback<Success> callback) {
								session.getClient().one()
										.commit(session.getClient())
										.and(new WhenCommitted() {

											@Override
											public void thenDo(
													final WithCommittedResult arg0) {
												callback.onSuccess(Success.INSTANCE);
											}

											@Override
											public void onFailure(
													final Throwable t) {
												callback.onFailure(Fn
														.exception(session, t));
											}

										});
							}
						});

		commitResult.get(new Closure<Success>() {

			@Override
			public void apply(final Success o) {

			}
		});

		return commitResult;
	}

	@Override
	public Result<Success> post(final Object value, final String toUri,
			final String secret) {
		final Result<Success> postResult = session.getOnedbEngine()
				.createResult(session.getExceptionManager(), session,
						new AsyncResult<Success>() {

							@Override
							public void get(final Callback<Success> callback) {
								assert value instanceof Serializable : "Post only supports Serializable values.";
								session.getClient()
										.one()
										.post((Serializable) value)
										.to(session.getClient().one()
												.reference(toUri))
										.withSecret(secret)
										.in(session.getClient())
										.and(new WhenMessagePosted() {

											@Override
											public void thenDo(
													final WithMessagePostedResult mr) {
												callback.onSuccess(Success.INSTANCE);
											}

											@Override
											public void onTargetNodeDoesNotExist(
													final WithTargetNodeDoesNotExistContext context) {
												callback.onUndefined(new UndefinedResult() {

													@Override
													public Object origin() {
														return session;
													}

													@Override
													public String message() {
														return "The node to be posted to does not exist.";
													}
												});
											}

											@Override
											public void onUnauthorized(
													final WithUnauthorizedContext context) {
												callback.onUnauthorized(H
														.fromUnauthorizedContext(
																session,
																context));
											}

											@Override
											public void onFailure(
													final Throwable t) {
												callback.onFailure(Fn
														.exception(session, t));
											}

										});
							}
						});

		postResult.get(new Closure<Success>() {

			@Override
			public void apply(final Success o) {
				// nothing
			}
		});

		return postResult;
	}

	@Override
	public Query seed(final String seedType) {
		final AsyncResult<Node> seedResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				session.getClient().runSafe(new Runnable() {

					@Override
					public void run() {

						session.getClient()
								.one()
								.seed(session.getClient(), seedType,
										new WhenSeeded() {

											@Override
											public void thenDo(
													final WithSeedResult r) {

												callback.onSuccess(session
														.getOnedbEngine()
														.getFactory()
														.createNode(
																session,
																session.getExceptionManager(),
																r.seedNode(),
																r.accessToken()));

											}

											@Override
											public void onFailure(
													final Throwable t) {
												callback.onFailure(Fn
														.exception(session, t));
											}

										});

					}
				});

			}
		};
		return session
				.getOnedbEngine()
				.getFactory()
				.createQuery(session, session.getExceptionManager(), seedResult);
	}

	@Override
	public Query seed() {
		return seed("default");
	}

	@Override
	public Result<Postbox> createPostbox(final String realmTitle,
			final String postboxType, final String apiKey) {
		final AsyncResult<Postbox> createPostboxResult = new AsyncResult<Postbox>() {

			@Override
			public void get(final Callback<Postbox> callback) {

				session.getClient().runSafe(new Runnable() {

					@Override
					public void run() {

						session.getClient().one().createRealm(realmTitle)
								.withType(postboxType).withApiKey(apiKey)
								.in(session.getClient())
								.and(new WhenRealmCreated() {

									@Override
									public void thenDo(
											final WithRealmCreatedResult cr) {
										callback.onSuccess(new Postbox() {

											@Override
											public String partnerSecret() {
												return cr.partnerSecret();
											}

											@Override
											public Node node() {
												return session
														.getOnedbEngine()
														.getFactory()
														.createNode(
																session,
																session.getExceptionManager(),
																session.getClient()
																		.one()
																		.reference(
																				cr.root()),
																cr.secret());
											}
										});

									}

									@Override
									public void onQuotaExceeded(
											final WithQuotaExceededContext context) {
										callback.onFailure(Fn
												.exception(
														session,
														new Exception(
																"Cannot create realm because quota for API key is exceeded.\n  Message: "
																		+ context
																				.message())));
									}

									@Override
									public void onFailure(final Throwable t) {
										callback.onFailure(Fn.exception(
												session, t));
									}

								});
					}
				});

			}
		};

		return session.getOnedbEngine().createResult(
				session.getExceptionManager(), session, createPostboxResult);
	}

	@Override
	public Query createRealm(final String realmTitle, final String realmType,
			final String apiKey) {
		final AsyncResult<Node> createRealmResult = new AsyncResult<Node>() {

			@Override
			public void get(final Callback<Node> callback) {

				session.getClient().runSafe(new Runnable() {

					@Override
					public void run() {

						session.getClient().one().createRealm(realmTitle)
								.withType(realmType).withApiKey(apiKey)
								.in(session.getClient())
								.and(new WhenRealmCreated() {

									@Override
									public void thenDo(
											final WithRealmCreatedResult cr) {
										callback.onSuccess(session
												.getOnedbEngine()
												.getFactory()
												.createNode(
														session,
														session.getExceptionManager(),
														session.getClient()
																.one()
																.reference(
																		cr.root()),
														cr.secret()));
									}

									@Override
									public void onQuotaExceeded(
											final WithQuotaExceededContext context) {
										callback.onFailure(Fn
												.exception(
														session,
														new Exception(
																"Cannot create realm because quota for API key is exceeded.\n  Message: "
																		+ context
																				.message())));
									}

									@Override
									public void onFailure(final Throwable t) {
										callback.onFailure(Fn.exception(
												session, t));
									}

								});
					}
				});

			}
		};
		return session
				.getOnedbEngine()
				.getFactory()
				.createQuery(session, session.getExceptionManager(),
						createRealmResult);
	}

	@Override
	public Result<Success> close() {

		final Result<Success> closeResult = session.getOnedbEngine()
				.createResult(session.getExceptionManager(), session,
						new AsyncResult<Success>() {

							@Override
							public void get(final Callback<Success> callback) {

								session.getClient().runSafe(new Runnable() {

									@Override
									public void run() {
										session.getClient().one()
												.commit(session.getClient())
												.and(new WhenCommitted() {

													@Override
													public void thenDo(
															final WithCommittedResult r) {
														session.getClient()
																.runSafe(
																		new Runnable() {

																			@Override
																			public void run() {
																				session.getClient()
																						.one()
																						.shutdown(
																								session.getClient())
																						.and(new WhenShutdown() {

																							@Override
																							public void thenDo() {
																								callback.onSuccess(Success.INSTANCE);
																							}

																							@Override
																							public void onFailure(
																									final Throwable t) {
																								callback.onFailure(Fn
																										.exception(
																												session,
																												t));
																							}

																						});
																			}
																		});

													}

													@Override
													public void onFailure(
															final Throwable t) {
														callback.onFailure(Fn
																.exception(
																		session,
																		t));
													}

												});

									}
								});

							}

						});

		closeResult.get(new Closure<Success>() {

			@Override
			public void apply(final Success o) {

			}

		});

		return closeResult;
	}

	@Override
	public Link node(final String uri) {
		return session.getOnedbEngine().getFactory()
				.createLink(session, null, uri, ""); // _NO_
		// parent
		// exception
		// Manager
	}

	@Override
	public Link node(final Link link) {
		return node(link.uri(), link.secret());
	}

	@Override
	public Link node(final Node node) {
		return node(node.uri(), node.secret());
	}

	@Override
	public Link node(final String uri, final String secret) {

		return session.getOnedbEngine().getFactory()
				.createLink(session, null, uri, secret);
	}

	@Override
	public Session getAll(final BasicResult<?>... results) {

		final Result<SuccessFail> callback = getAll(true, results);

		session.getClient().one().commit(session.getClient())
				.and(new WhenCommitted() {

					@Override
					public void thenDo(final WithCommittedResult r) {

					}

					@Override
					public void onFailure(final Throwable t) {
						session.getExceptionManager().onFailure(
								Fn.exception(session, t));
					}

				});

		final SuccessFail result = callback.get();

		if (result == null) {
			return session;
		}

		if (result.isFail()) {
			throw new RuntimeException(result.getException());
		}

		return session;
	}

	@Override
	public Result<SuccessFail> getAll(final boolean asynchronous,
			final BasicResult<?>... results) {
		final Result<SuccessFail> getAllResult = session.getEngine()
				.createResult(session.getExceptionManager(), session,
						new AsyncResult<SuccessFail>() {

							@SuppressWarnings({ "unchecked" })
							@Override
							public void get(final Callback<SuccessFail> callback) {

								final CallbackLatch latch = new CallbackLatch(
										results.length) {

									@Override
									public void onFailed(final Throwable arg0) {
										callback.onSuccess(SuccessFail
												.fail(arg0));
									}

									@Override
									public void onCompleted() {
										callback.onSuccess(SuccessFail
												.success());
									}
								};

								final BasicResult<Object>[] resultObj = (BasicResult<Object>[]) results;

								for (final BasicResult<Object> result : resultObj) {
									final EagerCallback<Object> eagerCallback = CallbackFactory
											.eagerCallback(
													session,
													session.getExceptionManager(),
													new Closure<Object>() {

														@Override
														public void apply(
																final Object o) {
															latch.registerSuccess();
														}

													}).catchExceptions(
													new ExceptionListener() {

														@Override
														public void onFailure(
																final ExceptionResult r) {
															latch.registerFail(r
																	.exception());
														}
													});
									result.get(eagerCallback);

								}

							}
						});

		return getAllResult;
	}

	@Override
	public LoginResult login(final String email, final String password) {

		return login(email, password,
				session.node("https://u1.linnk.it/0fs7dr/Apps1/appjangle"));
	}

	private interface AsyncLoginResult {
		public void get(final Callback<User> callback, LoginResult result);
	}

	@Override
	public LoginResult login(final String email, final String password,
			final Link application) {

		final AsyncLoginResult loginResult = new AsyncLoginResult() {

			@Override
			public void get(final Callback<User> callback,
					final LoginResult loginResult) {
				session.getClient().one()
						.loginUser(new LoginWithUserDetailsParameters() {

							@Override
							public String getPassword() {
								return password;
							}

							@Override
							public String getEmail() {
								return email;
							}

							@Override
							public OneClient getClient() {
								return session.getClient();
							}

							@Override
							public String getApplicationNodeUri() {
								return application.uri();
							}

							@Override
							public String getApplicationNodeSecret() {
								return application.getSecret();
							}

							@Override
							public WhenUserLoggedIn getCallback() {
								return createWhenUserLoggedInCallback(
										loginResult, callback);
							}
						});
			}
		};

		return createLoginResult(loginResult);

	}

	@Override
	public LoginResult login(final String sessionId) {
		return login(sessionId,
				session.node("https://u1.linnk.it/0fs7dr/Apps1/appjangle"));
	}

	@Override
	public LoginResult login(final String sessionId, final Link application) {
		final AsyncLoginResult loginResult = new AsyncLoginResult() {

			@Override
			public void get(final Callback<User> callback,
					final LoginResult loginResult) {
				session.getClient().one()
						.loginUser(new LoginWithSessionIdParameters() {

							@Override
							public String getSessionToken() {
								return sessionId;
							}

							@Override
							public OneClient getClient() {
								return session.getClient();
							}

							@Override
							public String getApplicationNodeUri() {
								return application.uri();
							}

							@Override
							public String getApplicationNodeSecret() {
								return application.getSecret();
							}

							@Override
							public WhenUserLoggedIn getCallback() {
								return createWhenUserLoggedInCallback(
										loginResult, callback);
							}

						});
			}
		};

		return createLoginResult(loginResult);
	}

	@Override
	public LoginResult register(final String email, final String password) {

		return register(email, password,
				session.node("https://u1.linnk.it/0fs7dr/Apps1/appjangle"));
	}

	@Override
	public LoginResult register(final String email, final String password,
			final Link application) {
		final AsyncLoginResult loginResult = new AsyncLoginResult() {

			@Override
			public void get(final Callback<User> callback,
					final LoginResult loginResult) {
				session.getClient().one()
						.registerUser(new RegisterUserParameters() {

							@Override
							public String getEmail() {
								return email;
							}

							@Override
							public String getPassword() {
								return password;
							}

							@Override
							public WhenUserRegistered getCallback() {
								return new WhenUserRegistered() {

									@Override
									public void onUserRegistered(
											final WithUserRegisteredResult urr) {
										callback.onSuccess(new User() {

											@Override
											public Link userNode() {

												return session.node(
														urr.userNodeUri(),
														urr.userNodeSecret());
											}

											@Override
											public String sessionToken() {

												return urr.sessionToken();
											}

											@Override
											public String email() {

												return urr.email();
											}

											@Override
											public Session session() {
												return session;
											}
										});
									}

									@Override
									public void onFailure(final Throwable t) {
										callback.onFailure(Fn
												.exception(this, t));
									}

									@Override
									public void onUserAlreadyRegistered() {
										if (loginResult
												.getLoginFailuresListener() == null) {
											onFailure(new Exception(
													"User cannot be registered because a user with the same e-mail address is already registered.\n"
															+ "To intercept session exception, define .catchLoginFailures."));
											return;
										}

										loginResult.getLoginFailuresListener()
												.onUserAlreadyRegistered();
									}

									@Override
									public void onChallenge(
											final WithChallengedContext context) {
										if (loginResult
												.getLoginFailuresListener() == null) {
											onFailure(new Exception(
													"Challenge for login received at: ["
															+ context
																	.challengeNodeUri()
															+ ":"
															+ context
																	.challengeNodeSecret()
															+ "].\n"
															+ "To intercept session exception, define .catchLoginFailures."));
											return;
										}

										loginResult.getLoginFailuresListener()
												.onChallenged(
														new ChallengedResult() {

															@Override
															public Link challengeLink() {
																return session
																		.node(context
																				.challengeNodeUri(),
																				context.challengeNodeSecret());
															}
														});
									}
								};
							}

							@Override
							public OneClient getClient() {
								return session.getClient();
							}

							@Override
							public String getApplicationNodeUri() {
								return application.uri();
							}

							@Override
							public String getApplicationNodeSecret() {
								return application.getSecret();
							}

						});
			}
		};

		return createLoginResult(loginResult);
	}

	private final LoginResult createLoginResult(
			final AsyncLoginResult asyncUserResult) {
		final LoginResultImpl loginResult = new LoginResultImpl();

		final AsyncResult<User> userResult = new AsyncResult<User>() {

			@Override
			public void get(final Callback<User> callback) {
				asyncUserResult.get(callback, loginResult);
			}
		};

		final Result<User> userResultImpl = session.getEngine().createResult(
				session.getExceptionManager(), session, userResult);

		loginResult.setUserResult(userResultImpl);

		return loginResult;
	}

	private final WhenUserLoggedIn createWhenUserLoggedInCallback(
			final LoginResult loginResult, final Callback<User> callback) {
		return new WhenUserLoggedIn() {

			@Override
			public void thenDo(final WithUserRegisteredResult result) {
				callback.onSuccess(new User() {

					@Override
					public Link userNode() {
						return session.node(result.userNodeUri(),
								result.userNodeSecret());
					}

					@Override
					public String sessionToken() {

						return result.sessionToken();
					}

					@Override
					public String email() {

						return result.email();
					}

					@Override
					public Session session() {
						return session;
					}
				});
			}

			@Override
			public void onFailure(final Throwable t) {
				callback.onFailure(Fn.exception(session, t));
			}

			@Override
			public void onNotRegisteredForApplication() {
				if (loginResult.getLoginFailuresListener() == null) {
					onFailure(new Exception(
							"User is not registered for application.\n"
									+ "To intercept session exception, define .catchLoginFailures."));
					return;
				}

				loginResult.getLoginFailuresListener()
						.onNotRegisteredForApplication();
			}

			@Override
			public void onInvalidDetails() {
				if (loginResult.getLoginFailuresListener() == null) {
					onFailure(new Exception(
							"Invalid user details.\n"
									+ "To intercept session exception, define .catchLoginFailures."));
					return;
				}

				loginResult.getLoginFailuresListener().onInvalidDetails();
			}

			@Override
			public void onChallenge(final WithChallengedContext context) {
				if (loginResult.getLoginFailuresListener() == null) {
					onFailure(new Exception(
							"Challenge for login received at: ["
									+ context.challengeNodeUri()
									+ ":"
									+ context.challengeNodeSecret()
									+ "].\n"
									+ "To intercept session exception, define .catchLoginFailures."));
					return;
				}

				loginResult.getLoginFailuresListener().onChallenged(
						new ChallengedResult() {

							@Override
							public Link challengeLink() {
								return session.node(context.challengeNodeUri(),
										context.challengeNodeSecret());
							}
						});
			}
		};
	}

}
