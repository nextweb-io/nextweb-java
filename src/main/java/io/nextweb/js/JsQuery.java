package io.nextweb.js;

import io.nextweb.Query;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.fn.JsResult;
import io.nextweb.js.operations.JsExceptionListeners;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsQuery implements Exportable, JsEntity<Query>,
		JsExceptionListeners<JsQuery> {

	private Query original;

	@NoExport
	public static JsQuery wrap(final Query query) {
		final JsQuery jsQuery = new JsQuery();
		jsQuery.setOriginal(query);
		return jsQuery;
	}

	public JsQuery() {
		super();
	}

	/*
	 * Js Entity common
	 */

	@Override
	@Export
	public Object get(final Object... params) {
		return JH.get(this, params);
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
	@NoExport
	public Query getOriginal() {
		return original;
	}

	@Override
	@NoExport
	public void setOriginal(final Query query) {
		this.original = query;
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

	@Override
	@Export
	public JsQuery catchExceptions(final JsClosure listener) {
		JsExceptionManager.wrap(original.getExceptionManager())
				.catchExceptions(listener);
		return this;
	}

	@Export
	@Override
	public JsQuery catchUndefined(final JsClosure undefinedListener) {
		JsExceptionManager.wrap(original.getExceptionManager()).catchUndefined(
				undefinedListener);
		return this;
	}

	@Export
	@Override
	public JsQuery catchUnauthorized(final JsClosure unauthorizedListener) {
		JsExceptionManager.wrap(original.getExceptionManager())
				.catchUnauthorized(unauthorizedListener);
		return this;
	}

	@Export
	@Override
	public JsQuery catchImpossible(final JsClosure impossibleListener) {
		JsExceptionManager.wrap(original.getExceptionManager())
				.catchImpossible(impossibleListener);
		return this;
	}

	@Export
	@Override
	public JsLinkListQuery selectAllLinks() {
		return JH.jsFactory(original).createLinkListQuery(
				original.selectAllLinks());
	}

	@Export
	@Override
	public JsListQuery selectAll(final JsLink propertyType) {
		return JH.jsFactory(original).createListQuery(
				original.selectAll(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsQuery select(final JsLink propertyType) {
		return JH.jsFactory(original).createQuery(
				original.select(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsListQuery selectAll() {
		return JH.jsFactory(original).createListQuery(original.selectAll());
	}

	@Export
	@Override
	public JsResult clearVersions(final int keepVersions) {
		return JH.jsFactory(original).createResult(
				original.clearVersions(keepVersions));
	}

	@Export
	@Override
	public JsResult remove(final Object entity) {
		return JH.op(original).remove().remove(entity);
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
