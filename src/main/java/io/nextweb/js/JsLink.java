package io.nextweb.js;

import io.nextweb.Link;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ExceptionResult;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.fn.JsResult;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLink implements Exportable, JsEntity<Link> {

	private Link original;

	@Export
	public String getUri() {
		return original.getUri();
	}

	@Export
	public String uri() {
		return original.uri();
	}

	@Override
	@NoExport
	public void setOriginal(final Link link) {
		this.original = link;
	}

	@Override
	@NoExport
	public Link getOriginal() {
		return this.original;
	}

	public JsLink() {
		super();
	}

	@NoExport
	public static JsLink wrap(final Link link) {
		final JsLink jsLink = new JsLink();
		jsLink.setOriginal(link);
		return jsLink;
	}

	/*
	 * Entity common
	 */

	@Override
	@Export
	public JsNodeListQuery selectAll() {
		return JH.jsFactory(original).createNodeListQuery(original.selectAll());
	}

	@Override
	@Export
	public void catchExceptions(final JsClosure listener) {
		original.catchExceptions(new ExceptionListener() {

			@Override
			public void onFailure(final ExceptionResult r) {
				listener.apply(r.exception());
			}
		});
	}

	@Override
	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
	}

	@Export
	@Override
	public Object append(final Object value) {
		return JH.op(original).append().append(value);
	}

	@Export
	@Override
	public Object append(final Object value, final String atAddress) {
		return JH.op(original).append().append(value, atAddress);
	}

	@Export
	@Override
	public Object appendValue(final Object value) {
		return JH.op(original).append().appendValue(value);
	}

	@Export
	@Override
	public JsQuery setValue(final Object newValue) {
		return JH.op(original).setValue().setValue(newValue);
	}

	@Export
	@Override
	public JsQuery value(final Object newValue) {
		return setValue(newValue);
	}

	@Export
	@Override
	public JsQuery setValueSafe(final Object newValue) {
		return JH.op(original).setValue().setValueSafe(newValue);
	}

	@Override
	@Export
	public JsQuery select(final JsLink propertyType) {
		return JH.jsFactory(propertyType.getOriginal()).createQuery(
				original.select(propertyType.getOriginal()));

	}

	@Export
	@Override
	public JsSession getSession() {
		return JH.jsFactory(original).createSession(original.getSession());
	}

	@Export
	@Override
	public JsExceptionManager getExceptionManager() {
		return JH.jsFactory(original).createExceptionManager(
				original.getExceptionManager());
	}

	@Export
	@Override
	public JsLinkListQuery selectAllLinks() {
		return JH.jsFactory(original).createLinkListQuery(
				original.selectAllLinks());
	}

	@Export
	@Override
	public JsNodeListQuery selectAll(final JsLink propertyType) {
		return JH.jsFactory(original).createNodeListQuery(
				original.selectAll(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsResult remove(final Object entity) {
		return JH.op(original).remove().remove(entity);
	}

	@Export
	@Override
	public JsResult clearVersions(final int keepVersions) {
		return JH.jsFactory(original).createResult(
				original.clearVersions(keepVersions));
	}

	@Export
	@Override
	public Object appendSafe(final Object value) {
		return JH.op(original).append().appendSafe(value);
	}

	@Export
	@Override
	public Object appendSafe(final Object value, final String atAddress) {
		return JH.op(original).append().appendSafe(value, atAddress);
	}

	@Export
	@Override
	public Object appendValueSafe(final Object value) {
		return JH.op(original).append().appendValueSafe(value);
	}

	@Export
	@Override
	public Object insert(final Object value, final int atIndex) {
		return JH.op(original).append().insert(value, atIndex);
	}

	@Export
	@Override
	public Object insert(final Object value, final String atAddress,
			final int atIndex) {
		return JH.op(original).append().insert(value, atAddress, atIndex);
	}

	@Export
	@Override
	public Object insertValue(final Object value, final int atIndex) {
		return JH.op(original).append().insertValue(value, atIndex);
	}

	@Export
	@Override
	public Object insertSafe(final Object value, final int atIndex) {
		return JH.op(original).append().insertSafe(value, atIndex);
	}

	@Export
	@Override
	public Object insertSafe(final Object value, final String atAddress,
			final int atIndex) {
		return JH.op(original).append().insertSafe(value, atAddress, atIndex);
	}

	@Export
	@Override
	public Object insertValueSafe(final Object value, final int atIndex) {
		return JH.op(original).append().insertValueSafe(value, atIndex);
	}

}
