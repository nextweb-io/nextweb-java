package io.nextweb;

import io.nextweb.operations.EntityRequestOperations;

public interface Link extends Entity, EntityRequestOperations {

	public String getUri();

	public String uri();

}
