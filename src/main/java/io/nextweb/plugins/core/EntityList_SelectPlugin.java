package io.nextweb.plugins.core;

import io.nextweb.EntityList;
import io.nextweb.operations.EntityListSelectOperations;
import io.nextweb.plugins.EntityListPlugin;

public interface EntityList_SelectPlugin<EntityListType extends EntityList<?>>
		extends EntityListPlugin<EntityListType>, EntityListSelectOperations {

}
