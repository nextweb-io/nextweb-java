package io.nextweb;

import io.nextweb.operations.entity.EntityRequestOperations;

/**
 * <p>
 * A Query is a special form of {@link Entity} representing the possibility of a
 * {@link Node}.
 * <p>
 * Specifically, calling the {@link #get()} method on a Query should return a
 * Node if there are no errors.
 * 
 * 
 * @see <a href="http://nextweb.io/docs/nextweb-entity.value.html">Entities
 *      (Nextweb API Documentation)</a>
 * @see <a href="http://nextweb.io/docs/nextweb-get.value.html">Get Operation
 *      (Nextweb API Documentation)</a>
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public interface Query extends Entity, EntityRequestOperations<Query> {

}
