package com.ononedb.nextweb;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.NodeListQuery;
import io.nextweb.Query;
import io.nextweb.fn.ExceptionListener;
import io.nextweb.fn.ResultCallback;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;

public class OnedbLinkListQuery implements LinkListQuery, OnedbEntityList {

	private final OnedbSession session;

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query select(Link propertyType) {
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

	@Override
	public LinkList get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void get(ResultCallback<LinkList> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catchExceptions(ExceptionListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OnedbSession getOnedbSession() {
		// TODO Auto-generated method stub
		return null;
	}

}
