package com.ononedb.nextweb.plugins;

import io.nextweb.Link;
import io.nextweb.LinkListQuery;
import io.nextweb.NodeListQuery;
import io.nextweb.plugins.core.Plugin_EntityList_Select;

import com.ononedb.nextweb.OnedbEntityList;

public class P_EntityList_Select implements
		Plugin_EntityList_Select<OnedbEntityList<?>> {

	private OnedbEntityList<?> list;

	public P_EntityList_Select() {
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
