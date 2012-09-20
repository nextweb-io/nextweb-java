package io.nextweb.plugins;

public interface Plugin<ForType> {

	/**
	 * Called immediately after creation of the Plugin to register the object
	 * this plugin was created for.
	 * 
	 * @param obj
	 */
	public void injectObject(ForType obj);

}
