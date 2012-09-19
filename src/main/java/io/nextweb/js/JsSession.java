package io.nextweb.js;

import io.nextweb.Session;
import io.nextweb.js.engine.JsNextwebEngine;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsSession implements Exportable {

	private Session session;

	@Export
	public JsNextwebEngine getEngine() {
		return JsNextwebEngine.wrap((NextwebEngineJs) session.getEngine());
	}

	@Export
	public JsResult close() {
		return JsResult.wrap(session.close());
	}

	@Export
	public JsLink node(String uri) {
		return JsLink.wrap(session.node(uri));
	}

	@NoExport
	public Session getSession() {
		return session;
	}

	@NoExport
	public void setSession(Session session) {
		this.session = session;
	}

	@NoExport
	public static JsSession wrap(Session session) {
		JsSession jsSession = new JsSession();
		jsSession.setSession(session);
		return jsSession;
	}

	public JsSession() {
		super();
	}

}
