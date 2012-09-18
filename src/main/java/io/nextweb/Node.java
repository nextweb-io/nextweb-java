package io.nextweb;

import io.nextweb.operations.EntityOperations;
import io.nextweb.plugins.HasPlugins;

public interface Node extends HasPlugins, EntityOperations {

	public String getUri();

	public String uri();

	public Object value();

	public Object getValue();

	public <ValueType> ValueType value(Class<ValueType> type);

}
