package io.nextweb.plugins.core;

import io.nextweb.Node;
import io.nextweb.operations.NodeAppendOperations;
import io.nextweb.plugins.EntityPlugin;

public interface Plugin_Node_Append<NodeType extends Node> extends
		NodeAppendOperations, EntityPlugin<NodeType> {

}
