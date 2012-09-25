package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLink implements Exportable, JsEntity<Link> {

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

	@Override
	@Export
	public Object get(Object... params) {
		return JH.get(this, params);
	}

	@Export
	@Override
	public Object append(Object value) {
		return JH.op(link).append().append(value);
	}

	@Export
	@Override
	public Object append(Object value, String atAddress) {
		return JH.op(link).append().append(value, atAddress);
	}

	@Export
	@Override
	public Object appendValue(Object value) {
		return JH.op(link).append().appendValue(value);
	}

	@Export
	@Override
	public JsQuery setValue(Object newValue) {
		return JH.op(link).setValue().setValue(newValue);
	}

	@Export
	@Override
	public JsQuery value(Object newValue) {
		return setValue(newValue);
	}

	@Export
	@Override
	public JsQuery setValueSafe(Object newValue) {
		return JH.op(link).setValue().setValueSafe(newValue);
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

	@Export
	@Override
	public JsResult remove(Object entity) {
		return JH.op(link).remove().remove(entity);
	}

	@Export
	@Override
	public JsResult clearVersions(int keepVersions) {
		return JH.jsFactory(link)
				.createResult(link.clearVersions(keepVersions));
	}
}
