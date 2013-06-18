/**
 * 
 */
package io.nextweb.engine.persistence;

/**
 * Callback to be called when a PersistenceService is closed.
 * 
 * @author Max Rohde
 * 
 */
public interface WhenClosed {

    /**
     * To be called if service is closed successfully.
     */
    public void thenDo();

    /**
     * To be called if any failure occured during closing the connection to the
     * service.
     * 
     * @param t
     */
    public void onFailure(Throwable t);
}