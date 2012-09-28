package io.nextweb;

import io.nextweb.operations.LinkOperations;
import io.nextweb.operations.entity.EntityRequestOperations;

public interface Link extends Entity, EntityRequestOperations<Link>,
		LinkOperations {

	public String getUri();

	public String uri();

	public String getSecret();

	// public <PluginType extends Plugin<?>> PluginType pluginTest(
	// PluginFactory<?, ? extends PluginType> factory);

}
