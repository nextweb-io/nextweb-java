package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.NodeListQuery;
import io.nextweb.plugins.core.EntityList_SelectPlugin;

import com.ononedb.nextweb.OnedbEntityList;

public class EntityListPlugin_Select implements
		EntityList_SelectPlugin<OnedbEntityList<?>> {

	private OnedbEntityList<?> list;

	public EntityListPlugin_Select() {
		super();

	}

	@Override
	public void injectObject(OnedbEntityList<?> obj) {
		this.list = obj;
	}

	@Override
	public NodeListQuery select(Link propertyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeListQuery selectAll(Link propertyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkListQuery selectAllLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeListQuery selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
