package io.nextweb.operations.entity;

import io.nextweb.Entity;
import io.nextweb.Query;
import io.nextweb.engine.fn.BooleanResult;

public interface EntityHasOperations {

    public BooleanResult has(Entity propertyType);

    public BooleanResult has(String path);

    public BooleanResult has(Entity propertyType, Class<?> type);

    public BooleanResult has(String path, Class<?> type);

    /**
     * 
     * @param propertyType
     * @param xsdType
     *            Use the name of a xsd data type in lower case, e.g. "string",
     *            "integer", "data", "boolean" etc.
     * @return
     */
    public BooleanResult has(Entity propertyType, String xsdType);

    /**
     * 
     * @param path
     * @param xsdType
     *            Use the name of a xsd data type in lower case, e.g. "string",
     *            "integer", "data", "boolean" etc.
     * @return
     */
    public BooleanResult has(String path, String xsdType);

    /**
     * Not supported yet.
     * 
     * @param propertyType
     * @return
     */
    public Query ifHas(Entity propertyType);

}
