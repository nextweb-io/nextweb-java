package io.nextweb.plugins.core;

import io.nextweb.Node;
import io.nextweb.operations.NodeAppendOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Node_AppendPlugin<NodeType extends Node> extends
		NodeAppendOperations, EntityPlugin<NodeType> {

}
