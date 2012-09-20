package com.ononedb.nextweb.plugins;

import io.nextweb.plugins.EntityListPlugin;

import com.ononedb.nextweb.OnedbEntityList;

public class EntityListPlugin_Select implements
		EntityListPlugin<OnedbEntityList> {

	private OnedbEntityList list;

	public EntityListPlugin_Select() {
		super();

	}

	@Override
	public void injectObject(OnedbEntityList obj) {
		this.list = obj;
	}

}
