/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package io.nextweb.engine.persistence.v01;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A composite of a node and its connections.
 * 
 * @author Max Rohde
 * 
 */
public class PersistedNode implements Serializable {

    private static final long serialVersionUID = 1L;

    public Serializable node;
    public List<String> connections;

    public PersistedNode(final Object node, final List<String> connections) {
        super();
        this.node = (Serializable) node;
        setConnections(connections);
    }

    public Object getNode() {
        return node;
    }

    public void setNode(final Serializable node) {
        this.node = node;
    }

    private final static List<String> EMPTY_STRING_LIST = Collections
            .unmodifiableList(new ArrayList<String>(0));

    public List<String> getConnections() {
        if (connections != null) {
            return connections;
        } else {
            return EMPTY_STRING_LIST;
        }
    }

    public void setConnections(final List<String> connections) {
        if (connections.size() > 0) {

            this.connections = new ArrayList<String>(connections);
        } else {
            this.connections = null;
        }
    }

    @Override
    public String toString() {
        return "PersistedNode(" + node + ", " + connections + ")";
    }

    /**
     * Use only for deserialization.
     */
    @Deprecated
    public PersistedNode() {
    }

}
