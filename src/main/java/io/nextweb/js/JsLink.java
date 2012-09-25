package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLink implements Exportable, JsEntity, JsWrapper<Link> {

	private Link link;

	@Override
	@Export
	public JsNodeListQuery selectAll() {
		return JH.jsFactory(link).createNodeListQuery(link.selectAll());
	}

	@Override
	@Export
	public void catchExceptions(final JsClosure listener) {
		link.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(Object origin, Throwable t) {
				listener.apply(t);
			}
		});
	}

	@Export
	public void get(final JsClosure callback) {
		JH.getNode(link, callback);
	}

	@Export
	public Object get() {
		return JH.getNode(link);
	}

	@Override
	@Export
	public JsQuery select(JsLink propertyType) {
		return JH.jsFactory(propertyType.getOriginal()).createQuery(
				link.select(propertyType.getOriginal()));

	}

	@Export
	public String getUri() {
		return link.getUri();
	}

	@Export
	public String uri() {
		return link.uri();
	}

	@Override
	@NoExport
	public void setOriginal(Link link) {
		this.link = link;
	}

	@Override
	@NoExport
	public Link getOriginal() {
		return this.link;
	}

	public JsLink() {
		super();
	}

	@NoExport
	public static JsLink wrap(Link link) {
		JsLink jsLink = new JsLink();
		jsLink.setOriginal(link);
		return jsLink;
	}

	@Export
	@Override
	public JsSession getSession() {
		return JH.jsFactory(link).createSession(link.getSession());
	}

	@Export
	@Override
	public JsExceptionManager getExceptionManager() {
		return JH.jsFactory(link).createExceptionManager(
				link.getExceptionManager());
	}

	@Export
	@Override
	public JsLinkListQuery selectAllLinks() {
		return JH.jsFactory(link).createLinkListQuery(link.selectAllLinks());
	}

	@Export
	@Override
	public JsNodeListQuery selectAll(JsLink propertyType) {
		return JH.jsFactory(link).createNodeListQuery(
				link.selectAll(propertyType.getOriginal()));
	}

}
