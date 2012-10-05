package io.nextweb.plugins.core;

import io.nextweb.EntityList;
import io.nextweb.operations.entitylist.EntityListSetValueOperations;
import io.nextweb.plugins.EntityListPlugin;

public interface Plugin_EntityList_SetValue<EntityListType extends EntityList>
		extends EntityListPlugin<EntityListType>, EntityListSetValueOperations {

}
