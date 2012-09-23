package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.PluginFactory;

import com.ononedb.nextweb.OnedbNode;

public class P_Node_Append_Factory implements
		PluginFactory<OnedbNode, P_Node_Append> {

	public static final P_Node_Append_Factory FACTORY = new P_Node_Append_Factory();

	@Override
	public P_Node_Append create(OnedbNode forObject) {
		P_Node_Append plugin = new P_Node_Append();
		plugin.injectObject(forObject);
		return plugin;
	}

}
