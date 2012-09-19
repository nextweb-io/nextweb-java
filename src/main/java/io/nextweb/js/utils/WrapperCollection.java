package io.nextweb.js.utils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.timepedia.exporter.client.ExporterUtil;

public class WrapperCollection {

	private final List<Wrapper> registeredWrappers;

	public void addWrapper(Wrapper wrapper) {
		registeredWrappers.add(wrapper);
	}

	public Object wrapForJs(Object gwtNode) {

		if (gwtNode instanceof String) {
			return gwtNode;
		}

		if (gwtNode instanceof Integer) {
			return gwtNode;
		}

		if (gwtNode instanceof Short) {
			return gwtNode;
		}

		if (gwtNode instanceof Long) {
			return gwtNode;
		}

		if (gwtNode instanceof Byte) {
			return gwtNode;
		}

		if (gwtNode instanceof Character) {
			return gwtNode;
		}

		if (gwtNode instanceof Double) {
			return gwtNode;
		}

		if (gwtNode instanceof Float) {
			return gwtNode;
		}

		if (gwtNode instanceof Date) {
			return gwtNode;
		}

		for (Wrapper wrapper : registeredWrappers) {
			if (wrapper.accepts(gwtNode)) {
				return wrapper.wrap(gwtNode);
			}
		}

		return ExporterUtil.wrap(gwtNode);
	}

	public WrapperCollection() {
		super();
		this.registeredWrappers = new LinkedList<Wrapper>();
	}

}
