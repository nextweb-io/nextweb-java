package io.nextweb.plugins.core;

import io.nextweb.EntityList;
import io.nextweb.operations.entitylist.EntityListSelectOperations;
import io.nextweb.plugins.EntityListPlugin;

public interface Plugin_EntityList_Select<EntityListType extends EntityList>
		extends EntityListPlugin<EntityListType>, EntityListSelectOperations {

}
