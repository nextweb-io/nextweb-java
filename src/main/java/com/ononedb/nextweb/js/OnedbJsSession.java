package com.ononedb.nextweb.js;

import io.nextweb.Link;
import io.nextweb.engine.NextwebEngine;
import io.nextweb.fn.Result;
import io.nextweb.fn.SuccessFail;
import io.nextweb.js.JsSession;
import io.nextweb.plugins.Plugin;
import io.nextweb.plugins.PluginFactory;

import org.timepedia.exporter.client.Export;

import com.ononedb.nextweb.OnedbSession;

@Export
public class OnedbJsSession implements JsSession {

	OnedbSession session;

	@Override
	public NextwebEngine getEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<SuccessFail> close() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link node(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<SuccessFail> getAll(Result<?>... results) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <PluginType extends Plugin> PluginType plugin(
			PluginFactory<PluginType> factory) {
		// TODO Auto-generated method stub
		return null;
	}

}
