package io.nextweb;

import io.nextweb.operations.EntityRequestOperations;
import io.nextweb.operations.LinkOperations;

public interface Link extends Entity, EntityRequestOperations, LinkOperations {

	public String getUri();

	public String uri();

}
