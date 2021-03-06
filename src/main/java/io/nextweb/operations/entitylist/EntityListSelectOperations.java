package io.nextweb.operations.entitylist;

import io.nextweb.Entity;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.engine.fn.BooleanResult;

public interface EntityListSelectOperations {

    public ListQuery select(Entity propertyType);

    public ListQuery select(String path);

    public ListQuery selectAll(Entity propertyType);

    public LinkListQuery selectAllLinks();

    public ListQuery selectAll();

    public BooleanResult has(Entity propertyType);

}
