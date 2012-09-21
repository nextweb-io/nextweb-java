package io.nextweb.js;

import io.nextweb.LinkList;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
public class JsLinkList implements Exportable, JsWrapper<LinkList> {

	private LinkList list;

	@NoExport
	@Override
	public LinkList getOriginal() {

		return list;
	}

	@NoExport
	@Override
	public void setOriginal(LinkList original) {
		this.list = original;
	}

	public static JsLinkList wrap(LinkList list) {
		JsLinkList jslist = new JsLinkList();
		jslist.setOriginal(list);
		return jslist;
	}

	public JsLinkList() {
		super();

	}

}
