package io.nextweb.js;

import io.nextweb.Session;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsSession implements Exportable {

	private Session session;

	@NoExport
	public Session getSession() {
		return session;
	}

	@NoExport
	public void setSession(Session session) {
		this.session = session;
	}

	public JsSession() {
		super();
	}

}
