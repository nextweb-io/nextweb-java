package io.nextweb.js;

import io.nextweb.Node;
import io.nextweb.js.common.JH;
import io.nextweb.js.common.operations.JsExceptionManager;
import io.nextweb.js.engine.NextwebEngineJs;
import io.nextweb.js.fn.JsClosure;
import io.nextweb.js.fn.JsResult;
import io.nextweb.js.operations.JsExceptionListeners;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsNode implements Exportable, JsEntity<Node>,
		JsExceptionListeners<JsNode> {

	private Node original;

	@Override
	@NoExport
	public Node getOriginal() {
		return original;
	}

	@Override
	@NoExport
	public void setOriginal(final Node node) {
		this.original = node;
	}

	@NoExport
	public static JsNode wrap(final Node node) {
		final JsNode jsNode = new JsNode();
		jsNode.setOriginal(node);
		return jsNode;
	}

	public JsNode() {
		super();
	}

	/*
	 * Js Interface
	 */

	@Export
	public boolean exists() {
		return original.exists();
	}

	@Export
	public String getUri() {
		return original.getUri();
	}

	@Export
	public String uri() {
		return getUri();
	}

	@Export
	public String getSecret() {
		return original.getSecret();
	}

	@Export
	public String secret() {
		return getSecret();
	}

	@Export
	public Object getValue() {
		return ((NextwebEngineJs) original.getSession().getEngine())
				.jsFactory().wrapValueObjectForJs(original.getValue());
	}

	@Export
	public Object value() {
		return getValue();
	}

	@Export
	public JsQuery reload() {
		return JH.jsFactory(original).createQuery(original.reload());
	}

	/*
	 * Entity common
	 */

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
	public JsNode catchExceptions(final JsClosure listener) {
		JsExceptionManager.wrap(original.getExceptionManager())
				.catchExceptions(listener);
		return this;
	}

	@Export
	@Override
	public JsNode catchUndefined(final JsClosure undefinedListener) {
		JsExceptionManager.wrap(original.getExceptionManager()).catchUndefined(
				undefinedListener);
		return this;
	}

	@Export
	@Override
	public JsNode catchUnauthorized(final JsClosure unauthorizedListener) {
		JsExceptionManager.wrap(original.getExceptionManager())
				.catchUnauthorized(unauthorizedListener);
		return this;
	}

	@Export
	@Override
	public JsNode catchImpossible(final JsClosure impossibleListener) {
		JsExceptionManager.wrap(original.getExceptionManager())
				.catchImpossible(impossibleListener);
		return this;
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
	public Object get(final Object... params) {
		return JH.get(this, params);
	}

	@Override
	@Export
	public JsResult remove(final Object entity) {
		return JH.op(original).remove().remove(entity);
	}

	@Override
	@Export
	public JsResult clearVersions(final int keepVersions) {
		return JH.jsFactory(original).createResult(
				original.clearVersions(keepVersions));
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
	public JsQuery select(final JsLink propertyType) {
		return JH.jsFactory(original).createQuery(
				original.select(propertyType.getOriginal()));
	}

	@Export
	@Override
	public JsNodeListQuery selectAll() {
		return JH.jsFactory(original).createNodeListQuery(original.selectAll());
	}

	@Override
	@Export
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
