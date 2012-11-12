package io.nextweb.nodes;

/**
 * A node which can hold any arbitrary byte data.
 * 
 * @author Max
 * 
 */
public interface Bytes {

	public byte[] getBytes();

	public String getMimeType();

}
