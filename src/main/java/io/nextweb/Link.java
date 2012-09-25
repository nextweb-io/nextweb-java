package io.nextweb;

import io.nextweb.operations.EntitySetValueOperations;
import io.nextweb.operations.LinkOperations;
import io.nextweb.operations.entity.EntityRequestOperations;

public interface Link extends Entity, EntityRequestOperations<Link>,
		EntitySetValueOperations<Link>, LinkOperations {

	public String getUri();

	public String uri();

}
